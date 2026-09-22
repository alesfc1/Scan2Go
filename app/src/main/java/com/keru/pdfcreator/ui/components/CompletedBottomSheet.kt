package com.keru.pdfcreator.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.keru.pdfcreator.R
import com.keru.pdfcreator.data.Document

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedDialog(
    document: Document,
    onDownload: () -> Unit = {},
    onShare: () -> Unit,
    onRename: (String) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    var showRenameDialog by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Add RenameDialog
    if (showRenameDialog) {
        RenameDialog(
            currentName = document.name,
            onDismiss = { showRenameDialog = false },
            onConfirm = { newName ->
                onRename(newName)
                showRenameDialog = false
            }
        )
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismiss() }
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Icon(
                painter = painterResource(id = R.drawable.completed),
                contentDescription = "",
                modifier = Modifier.size(72.dp),
                tint = Color(0xFFEE6E6F)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Your PDF has been created successfully!",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = document.name,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = Color.Gray
            )

            // Rename Button
            ElevatedButton(
                onClick = { showRenameDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Rename", color = Color(0xFF000000))
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Rename",
                    tint = Color(0xFF000000)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onDownload() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEE6E6F)
                )
            ){
                Text(text = "Download")
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Outlined.Download, contentDescription = ""
                )
            }

            Button(
                onClick = { onShare() },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF419EFC)
                )
            ) {
                Text(text = "Share")
                Spacer(modifier = Modifier.width(16.dp))
                Icon(imageVector = Icons.Outlined.Share, contentDescription = "")
            }

            ElevatedButton(onClick = { onDismiss() }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Done", color = Color(0xFFEE6E6F))
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
