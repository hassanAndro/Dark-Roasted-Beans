package com.example.coffeeit.ui.compose


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.coffeeit.R
import com.google.accompanist.insets.statusBarsPadding
import com.example.coffeeit.extensions.visible
import com.example.coffeeit.model.CoffeeMachine
import com.example.coffeeit.model.Type
import com.example.coffeeit.ui.main.MainRepository.Companion.COFFEE_MACHINE_ID
import com.example.coffeeit.ui.main.MainViewModel
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun CoffeeStyle(
    viewModel: MainViewModel,
    selectTypeId: (String) -> Unit
) {
    val coffeeMachineData: CoffeeMachine by viewModel.coffeeMachineData.collectAsState(
        initial = CoffeeMachine(id = COFFEE_MACHINE_ID)
    )
    val coffeeTypes: List<Type> = coffeeMachineData.types
    val isLoading: Boolean by viewModel.isLoading

    ConstraintLayout {
        val (title, styleText, coffeeList) = createRefs()

        // Title
        Text(
            text = stringResource(R.string.title),
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                }
                .padding(start = 14.dp, top = 15.dp)
        )

        // Subtitle for selection screen
        Text(
            text = stringResource(R.string.style_title),
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .constrainAs(styleText) {
                    top.linkTo(title.bottom)
                }
                .padding(start = 14.dp, top = 5.dp)
        )

        // Coffee style List
        val modifier = Modifier
            .constrainAs(coffeeList) {
                top.linkTo(styleText.bottom)
            }
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
        CoffeeStyleList(modifier, coffeeTypes, selectTypeId)

        // ProgressBar
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.visible(isLoading),
                color = colorResource(id = R.color.light_green)
            )
        }
    }
}

@Composable
private fun CoffeeStyleList(
    modifier: Modifier = Modifier,
    types: List<Type>,
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
                items = types,
                itemContent = { typeItem ->
                    CoffeeExtraItem(
                        type = typeItem,
                        selectTypeId = selectTypeId
                    )
                }
            )
        }
    }
}

@Composable
private fun CoffeeExtraItem(
    modifier: Modifier = Modifier,
    type: Type,
    selectTypeId: (String) -> Unit = {},
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable(
                onClick = { selectTypeId(type.id) }
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
            type.name?.let {
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
