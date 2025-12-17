package com.hyunjine.clothes.list

import android.content.Intent
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hyunjine.clothes.list.model.ClothesItemModel
import com.hyunjine.velo_android.loader.AndroidImageLoader
import com.hyunjine.velo_core.VeloImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ClothesScreen(
    modifier: Modifier = Modifier,
) {
    ClothesGrid(modifier = modifier)
}

@Composable
fun ClothesGrid(
    modifier: Modifier = Modifier,
    viewModel: ClothesViewModel = hiltViewModel()
) {

    val items by viewModel.clothes.collectAsStateWithLifecycle()
    ClothesGrid(
        modifier = modifier,
        clothes = items,
        onClickRemoveClothes = { id ->
            viewModel.removeClothes(id)
        }
    )
}

@Composable
fun ClothesGrid(
    modifier: Modifier = Modifier,
    clothes: List<ClothesItemModel>,
    onClickRemoveClothes: (Long) -> Unit,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    Box {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = modifier
        ) {
            items(clothes) { model ->
                ClothesItem(model = model, onClickRemoveClothes = onClickRemoveClothes)
            }
        }
        FloatingActionButton(
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 8.dp)
            ,
            onClick = { showBottomSheet = true }
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
        }
        if (showBottomSheet) {
            AddClothes { showBottomSheet = false }
        }
    }
}

@Composable
fun ClothesItem(
    model: ClothesItemModel,
    onClickRemoveClothes: (Long) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Column(
        modifier = Modifier.combinedClickable(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, model.link.toUri())
                context.startActivity(intent)
            },
            onLongClick = { showDialog = true }
        )
    ) {
        VeloImage(
            url = model.thumbnail.toString(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(16.dp)),
            imageLoader = AndroidImageLoader,
        )
        Text(
            text = model.description,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = Color.Black
        )
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    onClickRemoveClothes(model.id)
                    showDialog = false
                }) {
                    Text("삭제")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("취소")
                }
            },
            title = { Text("삭제할까요?") }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddClothes(
    viewModel: ClothesViewModel = hiltViewModel(),
    onDismissRequest: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        val clipboardManager = LocalClipboard.current
        val link by remember {
            mutableStateOf(
                clipboardManager.nativeClipboard.primaryClip?.getItemAt(0)?.text.toString()
            )
        }
        LaunchedEffect(link) {
            viewModel.updateLink(link)
        }
        val focusRequester = remember { FocusRequester() }

        val description by viewModel.description.collectAsStateWithLifecycle()

        val scrollState = rememberScrollState()

        LaunchedEffect(Unit) {
            delay(300)
            focusRequester.requestFocus()
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = link,
                onValueChange = { new -> viewModel.updateLink(new) }, // 값 변경 처리
                singleLine = true,
                label = { Text("링크") } // 라벨 (선택)
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                value = description,
                singleLine = true,
                onValueChange = { new -> viewModel.updateDescription(new) }, // 값 변경 처리
                label = { Text("설명") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done // Done, Go, Search, Next 등 선택 가능
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        scope.launch {
                            viewModel.addClothes()
                            sheetState.hide()
                            onDismissRequest()
                        }
                    }
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ClothesPreview() {
    val dummy = List(
        10
    ) {
        val link = "https://picsum.photos/400/200"
        ClothesItemModel(id = 0, link = link, thumbnail = link, description = "테스트입니다.")
    }
    ClothesGrid(
        clothes = dummy,
        onClickRemoveClothes = {}
    )
}