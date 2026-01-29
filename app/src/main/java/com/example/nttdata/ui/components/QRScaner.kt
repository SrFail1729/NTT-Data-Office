package com.example.nttdata.ui.theme.components

import android.widget.Toast
import androidx.camera.core.ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode

@Composable
fun QrScanner(
    modifier: Modifier,
    onQrDetected: (String) -> Unit
){
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    //Controlador de la cámara
    val cameraController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_ANALYSIS)//Nos permite detectar el QR
        }
    }

    //Configuración del escáner con ML Kit
    val barcodeScanner = remember {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE) //ML solo busca QRs
            .build()

        BarcodeScanning.getClient(options)
    }

    AndroidView(
        factory = {
            ctx ->
            PreviewView(ctx).apply {
                this.controller = cameraController //Conectamos la cámara a vista

                //Configuración del analizador
                cameraController.setImageAnalysisAnalyzer(
                    ContextCompat.getMainExecutor(ctx),
                    MlKitAnalyzer(
                        listOf(barcodeScanner),
                        COORDINATE_SYSTEM_VIEW_REFERENCED,
                        ContextCompat.getMainExecutor(ctx)
                    ){
                        result ->
                        //ML Detecta algo
                        val barcode = result?.getValue(barcodeScanner)?.firstOrNull()
                        val content = barcode?.rawValue
                        if (content != null){
                            Toast.makeText(ctx, "QR Detectado", Toast.LENGTH_SHORT).show()
                            onQrDetected(content)
                        }
                    }
                )

                //Inicamos la camara vinculada al ciclo de vida
                cameraController.bindToLifecycle(lifecycleOwner)
            }
        },
        modifier = modifier
    )
}