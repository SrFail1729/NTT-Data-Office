package com.example.nttdata.ui.screens.Menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nttdata.ui.theme.AppearanceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceScreen(
    viewModel: AppearanceViewModel,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            HeaderUsuarioMenu(onBack)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
        ) {
            Text(
                "Personalización",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Modo Oscuro
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            "Modo Oscuro",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "Cambia el tema de la aplicación",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Switch(
                        checked = viewModel.isDarkMode,
                        onCheckedChange = { viewModel.toggleDarkMode() }
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Tamaño de Fuente
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Tamaño de Fuente",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        "Ajusta el tamaño del texto en toda la app",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    Slider(
                        value = viewModel.fontScale,
                        onValueChange = { viewModel.updateFontScale(it) },
                        valueRange = 0.8f..1.5f,
                        steps = 5
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Pequeño", style = MaterialTheme.typography.labelSmall)
                        Text("Normal", style = MaterialTheme.typography.labelSmall)
                        Text("Grande", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
            
            Spacer(Modifier.weight(1f))
            
            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF070F26))
            ) {
                Text("Cerrar", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
