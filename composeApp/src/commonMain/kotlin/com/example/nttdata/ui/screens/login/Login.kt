package com.example.nttdata.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nttdata.data.local.SecurePreferences
import com.example.nttdata.domain.model.UserRole
import com.example.nttdata.isWebPlatform
import nttdata.composeapp.generated.resources.Res
import nttdata.composeapp.generated.resources.ntt_data
import org.jetbrains.compose.resources.painterResource

@Composable
fun Login(
    securePreferences: SecurePreferences,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel { LoginViewModel(securePreferences) },
    onLoginSuccess: (UserRole) -> Unit = {}
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(WindowInsets.safeDrawing.asPaddingValues())
            .padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(Res.drawable.ntt_data),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "NTT DATA Office",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(48.dp))

            Column(
                modifier = if (isWebPlatform()) {
                    Modifier.widthIn(max = 400.dp)
                } else {
                    Modifier.fillMaxWidth()
                },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    "Usuario (Correo)",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp).fillMaxWidth()
                )

                OutlinedTextField(
                    value = viewModel.username,
                    onValueChange = { newUsername: String -> viewModel.username = newUsername },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = Color(0xFF070F26),
                        unfocusedBorderColor = Color.Gray
                    ),
                    enabled = !viewModel.isLoading
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "Contraseña",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp).fillMaxWidth()
                )

                OutlinedTextField(
                    value = viewModel.password,
                    onValueChange = { newPassword: String -> viewModel.password = newPassword },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = Color(0xFF070F26),
                        unfocusedBorderColor = Color.Gray
                    ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                                tint = Color.Gray
                            )
                        }
                    },
                    enabled = !viewModel.isLoading
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.rememberMe = !viewModel.rememberMe }
                        .padding(vertical = 8.dp)
                ) {
                    Checkbox(
                        checked = viewModel.rememberMe,
                        onCheckedChange = { isChecked: Boolean ->
                            viewModel.rememberMe = isChecked
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFF070F26),
                            uncheckedColor = Color.Gray
                        )
                    )
                    Text(
                        text = "Recordar usuario",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                viewModel.errorMessage?.let { error ->
                    Text(
                        text = error,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.onLogin(onLoginSuccess)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF070F26)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    enabled = !viewModel.isLoading
                ) {
                    if (viewModel.isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(
                            text = "Inicio de sesión",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "¿Has olvidado tu contraseña?",
                    color = Color(0xFF0073BD),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}