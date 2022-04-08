package com.example.coffeeit.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.coffeeit.R
import com.example.coffeeit.model.Size
import com.example.coffeeit.ui.main.MainViewModel
import com.example.coffeeit.util.stringToList
import com.google.accompanist.insets.statusBarsPadding
import com.skydoves.landscapist.coil.CoilImage


@Composable
fun CoffeeSize(
    coffeeTypeId: String,
    viewModel: MainViewModel,
    selectTypeId: (String) -> Unit = {},
    pressOnBack: () -> Unit = {}
) {
    LaunchedEffect(key1 = coffeeTypeId) {
        viewModel.loadCoffeeSizesByIds(coffeeTypeId)
    }

    val sizeIds: String? by viewModel.coffeeSizesByIdFlow.collectAsState(initial = null)
    sizeIds?.let { ids ->
        val sizeIdList: List<String> = stringToList(ids)
        viewModel.loadCoffeeSizes(sizeIdList)
        val coffeeSizes: List<Size>? by viewModel.coffeeSizesFlow.collectAsState(initial = null)
        coffeeSizes?.let { sizes ->
            CoffeeExtraBody(coffeeTypeId, sizes, selectTypeId, pressOnBack)
        }
    }
}

@Composable
private fun CoffeeExtraBody(
    coffeeTypeId: String,
    coffeeSizesList: List<Size>,
    selectTypeId: (String) -> Unit = {},
    pressOnBack: () -> Unit
) {

    ConstraintLayout {
        val (backArrow, title, styleText, coffeeSizeList) = createRefs()

        Icon(
            imageVector = Icons.Filled.ArrowBack,
            tint = colorResource(id = R.color.black),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(backArrow) {
                    top.linkTo(parent.top)
                }
                .padding(start = 14.dp, top = 15.dp)
                .clickable(onClick = { pressOnBack() })
        )

        // Title
        Text(
            text = stringResource(R.string.title),
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(title) {
                    start.linkTo(backArrow.end)
                }
                .padding(start = 5.dp, top = 15.dp)
        )

        // Subtitle for selection screen
        Text(
            text = stringResource(R.string.size_title),
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .constrainAs(styleText) {
                    top.linkTo(title.bottom)
                }
                .padding(start = 14.dp, top = 5.dp)
        )

        // Coffee size List
        val modifier = Modifier
            .constrainAs(coffeeSizeList) {
                top.linkTo(styleText.bottom)
            }
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
        CoffeeExtrasList(coffeeTypeId, modifier, coffeeSizesList, selectTypeId)

    }

}

@Composable
private fun CoffeeExtrasList(
    coffeeTypeId: String,
    modifier: Modifier = Modifier,
    sizes: List<Size>,
    selectTypeId: (String) -> Unit = {},
) {
    val listState = rememberLazyListState()
    Column(
        modifier = modifier
            .statusBarsPadding()
            .background(MaterialTheme.colors.background)
    ) {
        LazyColumn(
            state = listState,
        ) {
            items(
                items = sizes,
                itemContent = { sizeItem ->
                    CoffeeExtraItem(
                        coffeeTypeId = coffeeTypeId,
                        size = sizeItem,
                        selectTypeId = selectTypeId
                    )
                }
            )
        }
    }
}


@Composable
private fun CoffeeExtraItem(
    coffeeTypeId: String,
    modifier: Modifier = Modifier,
    size: Size,
    selectTypeId: (String) -> Unit = {},
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable(
                onClick = { selectTypeId(coffeeTypeId) }
            ),
        color = colorResource(R.color.light_green),
        elevation = 8.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(12.dp)
        ) {
            val (image, coffeeTitle) = createRefs()

            // Dummy Coffee Image
            CoilImage(
                imageModel = R.drawable.ic_coffee,
                modifier = Modifier
                    .height(55.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(4.dp))
                    .constrainAs(image) {
                        centerVerticallyTo(parent)
                        end.linkTo(coffeeTitle.start)
                    },
            )

            // Coffee title
            size.name?.let {
                Text(
                    modifier = Modifier
                        .constrainAs(coffeeTitle) {
                            start.linkTo(image.end)
                        }
                        .padding(vertical = 20.dp, horizontal = 12.dp),
                    text = it,
                    color = colorResource(R.color.white),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }
}
