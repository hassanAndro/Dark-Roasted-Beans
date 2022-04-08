package com.example.coffeeit.ui.compose

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Black
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.coffeeit.R
import com.example.coffeeit.model.Extra
import com.example.coffeeit.model.Subselection
import com.example.coffeeit.ui.main.MainViewModel
import com.example.coffeeit.util.stringToList
import com.google.accompanist.insets.statusBarsPadding
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun CoffeeExtras(
    coffeeTypeId: String,
    viewModel: MainViewModel,
    selectTypeId: (String) -> Unit = {},
    pressOnBack: () -> Unit = {}
) {
    LaunchedEffect(key1 = coffeeTypeId) {
        viewModel.loadCoffeeExtrasByIds(coffeeTypeId)
    }

    val extrasIds: String? by viewModel.coffeeExtrasByIdFlow.collectAsState(initial = null)
    extrasIds?.let { ids ->
        val extrasIdsList: List<String> = stringToList(ids)
        viewModel.loadCoffeeExtras(extrasIdsList)
        val coffeeExtras: List<Extra>? by viewModel.coffeeExtrasFlow.collectAsState(initial = null)
        coffeeExtras?.let { extras ->
            CoffeeExtraBody(coffeeTypeId, extras, selectTypeId, pressOnBack)
        }
    }
}

@Composable
private fun CoffeeExtraBody(
    coffeeTypeId: String,
    coffeeExtrasList: List<Extra>,
    selectTypeId: (String) -> Unit = {},
    pressOnBack: () -> Unit
) {

    ConstraintLayout {
        val (backArrow, title, styleText, extrasList) = createRefs()

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
            text = stringResource(R.string.extras_title),
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .constrainAs(styleText) {
                    top.linkTo(title.bottom)
                }
                .padding(start = 14.dp, top = 5.dp)
        )

        // Coffee Extras List
        val modifier = Modifier
            .constrainAs(extrasList) {
                top.linkTo(styleText.bottom)
            }
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)


        CoffeeExtrasList(coffeeTypeId, modifier, coffeeExtrasList, selectTypeId)
    }
}

@Composable
private fun CoffeeExtrasList(
    coffeeTypeId: String,
    modifier: Modifier = Modifier,
    extras: List<Extra>,
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
                items = extras,
                itemContent = { coffeeExtrasItem ->
                    CoffeeExtraItem(
                        coffeeTypeId = coffeeTypeId,
                        extras = coffeeExtrasItem,
                        selectTypeId = selectTypeId
                    )

                    // Need time to work on expandable list
                    /*ExpandableCard(
                        extra = coffeeExtrasItem,
                        onCardArrowClick = { coffeeExtrasItem.id },
                        expanded = false,
                    )*/
                }
            )
        }
    }
}

@Composable
private fun CoffeeExtraItem(
    coffeeTypeId: String,
    modifier: Modifier = Modifier,
    extras: Extra,
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
            extras.name?.let {
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

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExpandableCard(
    extra: Extra,
    onCardArrowClick: () -> Unit,
    expanded: Boolean,
) {
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(transitionState, label = "transition")
    val cardBgColor by transition.animateColor({
        tween(durationMillis = 300)
    }, label = "bgColorTransition") {
        colorResource(id = R.color.light_green)
    }
    val cardPaddingHorizontal by transition.animateDp({
        tween(durationMillis = 300)
    }, label = "paddingTransition") { 4.dp }
    val cardElevation by transition.animateDp({
        tween(durationMillis = 300)
    }, label = "elevationTransition") { 8.dp }
    val cardRoundedCorners by transition.animateDp({
        tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    }, label = "cornersTransition") { 5.dp }
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = 300)
    }, label = "rotationDegreeTransition") {
        if (expanded) 0f else 180f
    }

    Card(
        backgroundColor = cardBgColor,
        contentColor = colorResource(id = R.color.light_green),
        elevation = cardElevation,
        shape = RoundedCornerShape(cardRoundedCorners),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = cardPaddingHorizontal,
                vertical = 8.dp
            )
    ) {
        /*Column {
            Box {
                CardArrow(
                    degrees = arrowRotationDegree,
                    onClick = onCardArrowClick
                )
                card.name?.let { CardTitle(title = it) }
            }
            ExpandableContent(visible = expanded)
        }*/
        ConstraintLayout(
            modifier = Modifier.padding(12.dp)
        ) {
            val (image, coffeeTitle, arrowIcon) = createRefs()

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
            extra.name?.let {
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

            // Arrow Icon
            IconButton(
                onClick = onCardArrowClick,
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_expand_less_24),
                        tint = colorResource(id = R.color.white),
                        contentDescription = "Expandable Arrow",
                        modifier = Modifier
                            .constrainAs(arrowIcon) {
                                centerVerticallyTo(parent)
                                end.linkTo(coffeeTitle.end)
                            }
                            .padding(vertical = 20.dp, horizontal = 12.dp)
                            .rotate(arrowRotationDegree),
                    )
                }
            )

            ExpandableContent(visible = expanded)
        }
    }
}

@Composable
fun CardTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textAlign = TextAlign.Center,
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandableContent(
    visible: Boolean = true,
) {
    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = 300,
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween(300))
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = 300,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(300))
    }
    AnimatedVisibility(
        visible = visible,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Spacer(modifier = Modifier.heightIn(100.dp))
            Text(
                text = "Expandable content here",
                textAlign = TextAlign.Center
            )
        }
    }
}
