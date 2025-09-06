package io.kdomskia.playground

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.toRoute
import androidx.window.core.layout.WindowWidthSizeClass
import io.kdomskia.compose.KdomskiaApp
import io.kdomskia.compose.foundation.Image
import io.kdomskia.compose.foundation.ScrollState
import io.kdomskia.compose.foundation.background
import io.kdomskia.compose.foundation.dom.DomOverflow
import io.kdomskia.compose.foundation.dom.DomScrollOptions
import io.kdomskia.compose.foundation.dom.DomScrollTarget
import io.kdomskia.compose.foundation.horizontalScroll
import io.kdomskia.compose.foundation.layout.Arrangement
import io.kdomskia.compose.foundation.layout.Box
import io.kdomskia.compose.foundation.layout.Column
import io.kdomskia.compose.foundation.layout.IntrinsicSize
import io.kdomskia.compose.foundation.layout.Row
import io.kdomskia.compose.foundation.layout.WindowInsets
import io.kdomskia.compose.foundation.layout.asPaddingValues
import io.kdomskia.compose.foundation.layout.fillMaxHeight
import io.kdomskia.compose.foundation.layout.fillMaxSize
import io.kdomskia.compose.foundation.layout.fillMaxWidth
import io.kdomskia.compose.foundation.layout.fillViewportHeight
import io.kdomskia.compose.foundation.layout.height
import io.kdomskia.compose.foundation.layout.padding
import io.kdomskia.compose.foundation.layout.safeDrawing
import io.kdomskia.compose.foundation.layout.size
import io.kdomskia.compose.foundation.layout.width
import io.kdomskia.compose.foundation.rememberScrollState
import io.kdomskia.compose.foundation.shape.CircleShape
import io.kdomskia.compose.foundation.shape.RoundedCornerShape
import io.kdomskia.compose.foundation.verticalScroll
import io.kdomskia.compose.helper.ComposableWhen
import io.kdomskia.compose.helper.get
import io.kdomskia.compose.helper.getComposable
import io.kdomskia.compose.material3.Button
import io.kdomskia.compose.material3.ElevatedButton
import io.kdomskia.compose.material3.ExtendedFloatingActionButton
import io.kdomskia.compose.material3.FloatingActionButton
import io.kdomskia.compose.material3.Icon
import io.kdomskia.compose.material3.IconButton
import io.kdomskia.compose.material3.LocalTextStyle
import io.kdomskia.compose.material3.MaterialTheme
import io.kdomskia.compose.material3.OutlinedButton
import io.kdomskia.compose.material3.PrimaryScrollableTabRow
import io.kdomskia.compose.material3.PrimaryTabRow
import io.kdomskia.compose.material3.ProvideContentColor
import io.kdomskia.compose.material3.ProvideTextStyle
import io.kdomskia.compose.material3.SecondaryScrollableTabRow
import io.kdomskia.compose.material3.SecondaryTabRow
import io.kdomskia.compose.material3.Tab
import io.kdomskia.compose.material3.Text
import io.kdomskia.compose.material3.TopAppBar
import io.kdomskia.compose.material3.TopAppBarDefaults
import io.kdomskia.compose.navigation.NavExternalLink
import io.kdomskia.compose.navigation.settings.Navigation
import io.kdomskia.compose.resource.DrawableResourcesRef
import io.kdomskia.compose.ui.Alignment
import io.kdomskia.compose.ui.Modifier
import io.kdomskia.compose.ui.domOnly
import io.kdomskia.compose.ui.draw.clip
import io.kdomskia.compose.ui.graphics.supportsVectorDrawable
import io.kdomskia.compose.ui.layout.onGloballyPositioned
import io.kdomskia.compose.ui.resource.img
import io.kdomskia.compose.ui.settings.SkiaUiSettingsScope
import io.kdomskia.compose.ui.settings.Ui
import io.kdomskia.compose.ui.skiaOnly
import io.kdomskia.compose.ui.then
import io.kdomskia.navigation.compose.NavHost
import io.kdomskia.navigation.compose.composable
import io.kdomskia.navigation.compose.rememberNavController
import io.kdomskia.playground.resource.RemoteImageRes
import io.kdomskia.playground.theme.AppTheme
import io.kdomskia.playground.viewmodel.SampleViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private val groupHeight = 200.dp
private val imageHeight = 130.dp
private const val boxItemSizeFraction = 0.4f

@Composable
fun App() {
    val navController = rememberNavController()
    var darkTheme by remember { mutableStateOf(false) }
    KoinApplication(
        application = {
            modules(
                module {
                    viewModel {
                        SampleViewModel()
                    }
                }
            )
        }
    ) {
        KdomskiaApp(
            drawableResources = DrawableResourcesRef(
                all = Res.allDrawableResources,
                onGetUri = Res::getUri
            ),
            settings = {
                Ui {
                    skia {
                        skiaCustomSettings()
                    }
                }
                Navigation {
                    dom {
                        bindToWindow = true //Default is also true
                    }
                }
            }
        ) {
            AppTheme(
                darkTheme = darkTheme
            ) {
//                val colors = NavigationRailItemColors(
//                    selectedIconColor = Color.Red,
//                    selectedTextColor = Color.Green,
//                    selectedIndicatorColor = Color.Yellow,
//                    unselectedIconColor = Color.Blue,
//                    unselectedTextColor = Color.Cyan,
//                    disabledIconColor = Color.DarkGray,
//                    disabledTextColor = Color.LightGray
//                )
//                Row(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(Color.Yellow)
//                ) {
//                    NavigationRail(
//                        header = {
//                            IconButton(
//                                onClick = { }
//                            ) {
//                                Icon(
//                                    img = Res.drawable.search.img,
//                                    contentDescription = null
//                                )
//                            }
//                        }
//                    ) {
//                        NavigationRailItem(
//                            enabled = true,
//                            selected = true,
//                            alwaysShowLabel = false,
//                            onClick = { },
//                            icon = {
//                                Icon(
//                                    img = Res.drawable.search.img,
//                                    contentDescription = null
//                                )
//                            },
//                            label = {
//                                Text(
//                                    text = "E:t, S:t"
//                                )
//                            },
//                            colors = colors
//                        )
//                        NavigationRailItem(
//                            enabled = true,
//                            selected = false,
//                            alwaysShowLabel = false,
//                            onClick = { },
//                            icon = {
//                                Icon(
//                                    img = Res.drawable.search.img,
//                                    contentDescription = null
//                                )
//                            },
//                            label = {
//                                Text(
//                                    text = "E:t, S:f"
//                                )
//                            },
//                            colors = colors
//                        )
//                        NavigationRailItem(
//                            enabled = false,
//                            selected = false,
//                            onClick = { },
//                            icon = {
//                                Icon(
//                                    img = Res.drawable.search.img,
//                                    contentDescription = null
//                                )
//                            },
//                            label = {
//                                Text(
//                                    text = "E:f, S:f"
//                                )
//                            },
//                            colors = colors
//                        )
//                        NavigationRailItem(
//                            enabled = false,
//                            selected = true,
//                            onClick = { },
//                            icon = {
//                                Icon(
//                                    img = Res.drawable.search.img,
//                                    contentDescription = null
//                                )
//                            },
//                            label = {
//                                Text(
//                                    text = "E:f, S:t"
//                                )
//                            },
//                            colors = colors
//                        )
//                    }
//                }
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(Color.Red)
//                ) {
//                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//                    val scope = rememberCoroutineScope()
//                    ModalNavigationDrawer(
//                        drawerState = drawerState,
//                        drawerContent = {
//                            ModalDrawerSheet {
//                                Column(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .padding(horizontal = 16.dp)
//                                        .verticalScroll(
//                                            state = rememberScrollState(),
//                                            domOptions = DomScrollOptions(
//                                                DomScrollTarget.Element
//                                            )
//                                        )
//                                ) {
//                                    Spacer(Modifier.height(12.dp))
//                                    Text("Drawer Title", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
//                                    HorizontalDivider()
//
//                                    Text("Section 1", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
//                                    NavigationDrawerItem(
//                                        label = { Text("Item 1") },
//                                        selected = false,
//                                        onClick = { /* Handle click */ }
//                                    )
//                                    NavigationDrawerItem(
//                                        label = { Text("Item 2") },
//                                        selected = false,
//                                        onClick = { /* Handle click */ }
//                                    )
//
//                                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
//
//                                    Text("Section 2", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
//                                    NavigationDrawerItem(
//                                        label = { Text("Settings") },
//                                        selected = true,
//                                        icon = { Icon(Res.drawable.search.img, contentDescription = null) },
//                                        badge = { Text("20") }, // Placeholder
//                                        onClick = { /* Handle click */ }
//                                    )
//                                    NavigationDrawerItem(
//                                        label = { Text("Help and feedback") },
//                                        selected = false,
//                                        icon = { Icon(Res.drawable.search.img, contentDescription = null) },
//                                        onClick = { /* Handle click */ },
//                                    )
//                                    Spacer(Modifier.height(12.dp))
//                                }
//                            }
//                        }
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .background(Color.Blue),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Button(
//                                onClick = {
//                                    scope.launch {
//                                        when (drawerState.currentValue) {
//                                            DrawerValue.Closed -> {
//                                                drawerState.open()
//                                            }
//
//                                            DrawerValue.Open -> {
//                                                drawerState.close()
//                                            }
//                                        }
//                                    }
//                                }
//                            ) {
//                                Text("Toggle")
//                            }
//                        }
//                    }
//                }
//
//                val recipes = remember { (1..10).toList() }
//                var customItemHeight by remember { mutableStateOf(200) }
//
//                LaunchedEffect(Unit) {
//                    var increment = true
//                    while (true) {
//                        if (increment) {
//                            customItemHeight = customItemHeight + 1
//                        } else {
//                            customItemHeight = customItemHeight - 1
//                        }
//
//                        if (customItemHeight <= 150)
//                            increment = true
//
//                        if (customItemHeight >= 250)
//                            increment = false
//
//                        delay(20L)
//                    }
//                }
//
//                LazyVerticalStaggeredGrid(
//                    columns = StaggeredGridCells.Fixed(3),
//                    modifier = Modifier.fillMaxWidth(),
//                    contentPadding = PaddingValues(8.dp),
//                    verticalItemSpacing = 8.dp,
//                    horizontalArrangement = Arrangement.spacedBy(64.dp)
//                ) {
//                    items(recipes) { recipe ->
//                        val height = if (recipe == 2)
//                            customItemHeight.dp
//                        else
//                            200.dp
//
//                        Box(
//                            modifier = Modifier
//                                .height(height)
//                                .fillMaxWidth()
//                                .padding(8.dp),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .background(Color.DarkGray),
//                                text = "Item $recipe",
//                                textAlign = TextAlign.Center
//                            )
//                        }
//                    }
//                }

                ProvideContentColor(MaterialTheme.colorScheme.onBackground) {
                    NavHost(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background),
                        navController = navController,
                        startDestination = Route.Home::class
                    ) {
                        composable<Route.Home> {
                            HomeScreen(
                                onDetailClick = { id ->
                                    navController.navigate(
                                        Route.Detail(id)
                                    ) {
                                        launchSingleTop = true
                                    }
                                },
                                onToggleTheme = {
                                    darkTheme = darkTheme.not()
                                }
                            )
                        }
                        composable<Route.Detail> {
                            val detail = it.toRoute<Route.Detail>()
                            DetailScreen(
                                id = detail.id,
                                onBack = {
                                    navController.navigateUp()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    onDetailClick: (String) -> Unit,
    onToggleTheme: () -> Unit
) {
    val safePadding = WindowInsets.safeDrawing.asPaddingValues()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(
                state = scrollState,
                domOptions = DomScrollOptions(
                    target = DomScrollTarget.Window
                )
            )
            .padding(safePadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        LayoutSample()
        TypographySample()
        LocalImagesSample()
        RemoteImageSample()
        ShapeSample()
        MaterialComponentsSample(onToggleTheme)
        ViewModelSample()
        NavigationSample(onDetailClick)
        WindowScrollSample(scrollState)
        InnerScrollSample()
        DomSkiaSpecificCodeSample()
        SizeClassSample()
        TopAppBarSample()
        TabsSample()
    }
}

@Composable
fun LayoutSample() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Layout")
        ColumnSample()
        RowSample()
        BoxSample()
    }
}

@Composable
fun ColumnSample() {
    Box(
        modifier = Modifier
            .height(groupHeight)
            .fillMaxWidth()
            .background(Color.Blue)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .background(Color.Yellow)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .background(Color.Red)
                    .weight(1f)
                    .align(Alignment.Start)
            ) { }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.34f)
                    .background(Color.Green)
                    .weight(1f)
            ) { }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .background(Color.LightGray)
                    .weight(1f)
                    .align(Alignment.End)
            ) { }
        }
    }
}

@Composable
fun RowSample() {
    Box(
        modifier = Modifier
            .height(groupHeight)
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Yellow),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .background(Color.Red)
                    .weight(1f)
                    .align(Alignment.Bottom)
            ) { }
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.34f)
                    .background(Color.Green)
                    .weight(1f)
            ) { }
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .background(Color.LightGray)
                    .weight(1f)
                    .align(Alignment.Top)
            ) { }
        }
    }
}

@Composable
fun BoxSample() {
    Box(
        modifier = Modifier
            .height(groupHeight)
            .fillMaxWidth()
            .background(Color.Green)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Yellow),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Cyan)
                    .fillMaxSize(boxItemSizeFraction)
                    .align(Alignment.TopEnd),
            ) { }
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize(boxItemSizeFraction)
                    .align(Alignment.BottomStart),
            ) { }
            Box(
                modifier = Modifier
                    .background(Color.Green)
                    .fillMaxSize(boxItemSizeFraction)
            ) { }
            Box(
                modifier = Modifier
                    .background(Color.Red)
                    .fillMaxSize(boxItemSizeFraction)
                    .align(Alignment.TopStart)
            ) { }
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxSize(boxItemSizeFraction)
                    .align(Alignment.BottomEnd),
            ) { }
        }
    }
}

@Composable
fun TypographySample() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Typography")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            ProvideContentColor(MaterialTheme.colorScheme.onSecondaryContainer) {
                Text(
                    modifier = Modifier.onGloballyPositioned {

                    },
                    text = "Display Large Display Large Display Large Display Large Display Large",
                    style = MaterialTheme.typography.displayLarge,
                    maxLines = 2
                )
                Text(
                    text = "Display Small",
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = "Title Large",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Title Medium",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun LocalImagesSample() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Local Image"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "PNG"
                )
                Image(
                    img = Res.drawable.compose_png.img,
                    modifier = Modifier.height(imageHeight),
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "SVG"
                )
                Image(
                    img = Res.drawable.compose_svg.img,
                    modifier = Modifier.height(imageHeight),
                    contentDescription = null
                )
            }
            if (supportsVectorDrawable()) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "XML"
                    )
                    Image(
                        img = Res.drawable.compose_xml.img,
                        modifier = Modifier.height(imageHeight),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun RemoteImageSample() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Remote Image"
        )
        Image(
            img = RemoteImageRes.jetpackCompose,
            modifier = Modifier
                .padding(16.dp)
                .size(
                    width = 434.dp,
                    height = 160.dp
                ),
            contentDescription = null
        )
    }
}

@Composable
fun ShapeSample() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Shape"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    img = Res.drawable.cat.img,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clip(CircleShape)
                        .size(imageHeight),
                    contentDescription = null
                )
            }
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    img = Res.drawable.cat.img,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(8.dp))
                        .size(imageHeight),
                    contentDescription = null
                )
            }
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    img = Res.drawable.cat.img,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clip(
                            RoundedCornerShape(
                                topStartPercent = 20,
                                topEndPercent = 0,
                                bottomEndPercent = 20,
                                bottomStartPercent = 0
                            )
                        )
                        .size(imageHeight),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun MaterialComponentsSample(
    onToggleTheme: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Material Components")
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { }
                ) {
                    Text("Filled")
                }
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                OutlinedButton(
                    onClick = { }
                ) {
                    Text("Outlined")
                }
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                ElevatedButton(
                    onClick = { }
                ) {
                    Text("Elevated")
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.weight(0.9f),
                contentAlignment = Alignment.Center
            ) {
                FloatingActionButton(
                    onClick = { }
                ) {
                    Icon(
                        img = Res.drawable.search.img,
                        contentDescription = null
                    )
                }
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                ExtendedFloatingActionButton(
                    text = { Text("Extended") },
                    icon = {
                        Icon(
                            img = Res.drawable.search.img,
                            contentDescription = null
                        )
                    },
                    onClick = { }
                )
            }
            Box(
                modifier = Modifier.weight(0.9f),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        img = Res.drawable.search.img,
                        contentDescription = null
                    )
                }
            }
        }
        Button(
            onClick = onToggleTheme
        ) {
            Text("Toggle Theme")
        }
    }
}

@Composable
fun ViewModelSample() {
    val viewModel = koinViewModel<SampleViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("ViewModel")
        Text("State: $state")
    }
}

@Composable
fun NavigationSample(
    onDetailClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Navigation")
        Button(
            onClick = {
                val id = "1"
                onDetailClick(id)
            }
        ) {
            Text("Show Detail Screen")
        }
        //TODO: add sample with NavLink
        NavExternalLink(
            uri = "https://google.com"
        ) {
            Button(
                onClick = navigate()
            ) {
                Text("Open External Link")
            }
        }
    }
}

@Composable
fun WindowScrollSample(
    scrollState: ScrollState
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Scroll (Window)")
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    scope.launch {
                        scrollState.scrollTo(0)
                    }
                }
            ) {
                Text("Scroll to Top")
            }
            Button(
                onClick = {
                    scope.launch {
                        scrollState.animateScrollTo(0)
                    }
                }
            ) {
                Text("Scroll to Top (Animate)")
            }
        }
    }
}

@Composable
fun InnerScrollSample() {
    val scope = rememberCoroutineScope()
    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Scroll (Inner)")
        Box(
            modifier = Modifier
                .size(200.dp)
                .verticalScroll(
                    state = verticalScrollState,
                    domOptions = DomScrollOptions(
                        target = DomScrollTarget.Element,
                        overflow = DomOverflow.Scroll
                    )
                )
                .horizontalScroll(
                    state = horizontalScrollState,
                    domOptions = DomScrollOptions(
                        target = DomScrollTarget.Element,
                        overflow = DomOverflow.Scroll
                    )
                )
        ) {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .background(Color(0xFF8B42ED))
            ) {
                Text(
                    modifier = Modifier.align(Alignment.TopStart),
                    text = "TopStart",
                    color = Color.White
                )
                Text(
                    modifier = Modifier.align(Alignment.TopCenter),
                    text = "TopCenter",
                    color = Color.White
                )
                Text(
                    modifier = Modifier.align(Alignment.TopEnd),
                    text = "TopEnd",
                    color = Color.White
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterStart),
                    text = "CenterStart",
                    color = Color.White
                )
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Center",
                    color = Color.White
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    text = "CenterEnd",
                    color = Color.White
                )
                Text(
                    modifier = Modifier.align(Alignment.BottomStart),
                    text = "BottomStart",
                    color = Color.White
                )
                Text(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    text = "BottomCenter",
                    color = Color.White
                )
                Text(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    text = "BottomEnd",
                    color = Color.White
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    scope.launch {
                        verticalScrollState.scrollTo(Int.MAX_VALUE)
                    }
                }
            ) {
                Text("Scroll to Bottom")
            }
            Button(
                onClick = {
                    scope.launch {
                        horizontalScrollState.scrollTo(Int.MAX_VALUE)
                    }
                }
            ) {
                Text("Scroll to End")
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    scope.launch {
                        verticalScrollState.animateScrollTo(0)
                    }
                }
            ) {
                Text("Scroll to Top (Animate)")
            }
            Button(
                onClick = {
                    scope.launch {
                        horizontalScrollState.animateScrollTo(0)
                    }
                }
            ) {
                Text("Scroll to Start (Animate)")
            }
        }
    }
}

@Composable
fun DomSkiaSpecificCodeSample() {
    Column(
        modifier = Modifier.width(IntrinsicSize.Max),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = "Dom / Skia specific code"
        )

        ProvideTextStyle(
            LocalTextStyle.current.copy(
                color = Color.White,
                textAlign = TextAlign.Center
            )
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        dom = { background(Color(0xFF8B42ED)) },
                        skia = { background(Color(0xFF62BAF7)) }
                    )
                    .padding(2.dp),
                text = "This piece of code"
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .domOnly { background(Color(0xFF9944D2)) }
                    .skiaOnly { background(Color(0xFF63A6F7)) }
                    .padding(2.dp),
                text = "shows that"
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        get(
                            dom = { Color(0xFFA747B7) },
                            skia = { Color(0xFF6493F7) }
                        )
                    )
                    .padding(2.dp),
                text = "there are"
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        getComposable(
                            dom = { someDomComposableModifier() },
                            skia = { someSkiaComposableModifier() }
                        )
                    )
                    .padding(2.dp),
                text = "several ways"
            )
            ComposableWhen(
                dom = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFC34C80))
                            .padding(2.dp),
                        text = "to write Dom / Skia"
                    )
                },
                skia = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF676CF6))
                            .padding(2.dp),
                        text = "to write Dom / Skia"
                    )
                }
            )
            ExpectActualComposable()
        }
    }
}

@Composable
fun someDomComposableModifier(): Modifier = Modifier.background(Color(0xFFB5499B))

@Composable
fun someSkiaComposableModifier(): Modifier = Modifier.background(Color(0xFF667FF6))

@Composable
expect fun ExpectActualComposable()

@Composable
fun SizeClassSample() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Material3 Adaptive Layout")
        ByWidthSizeClass(
            compact = {
                Text("Window width: Compact")
            },
            medium = {
                Text("Window width: Medium")
            },
            expanded = {
                Text("Window width: Expanded")
            }
        )
        Text("Resize the window and see the Width Size Class to change")
    }
}

@Composable
fun DetailScreen(
    id: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillViewportHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Detail Screen",
            textAlign = TextAlign.Center
        )
        Text(
            text = "Id: $id",
            textAlign = TextAlign.Center
        )
        Button(
            onClick = onBack
        ) {
            Text("Back")
        }
    }
}

@Composable
fun ByWidthSizeClass(
    compact: @Composable () -> Unit,
    medium: @Composable () -> Unit,
    expanded: @Composable () -> Unit
) {
    val sizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass

    when (sizeClass) {
        WindowWidthSizeClass.EXPANDED -> expanded()
        WindowWidthSizeClass.MEDIUM -> medium()
        else -> compact()
    }
}

@Composable
expect fun SkiaUiSettingsScope.skiaCustomSettings()

object Route {

    @Serializable
    @SerialName("home")
    object Home

    @Serializable
    @SerialName("detail")
    data class Detail(
        val id: String
    )

}

@Composable
fun TabsSample() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Primary()
        Secondary()
        PrimaryScrollable()
        SecondaryScrollable()
        PrimaryScrollableEdge()
        SecondaryScrollableEdge()
    }
}

@Composable
fun TopAppBarSample() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = { Text("Title") },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        img = Res.drawable.search.img,
                        contentDescription = ""
                    )
                }
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        img = Res.drawable.search.img,
                        contentDescription = ""
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        img = Res.drawable.search.img,
                        contentDescription = ""
                    )
                }
            },
            windowInsets = WindowInsets(),
            colors = TopAppBarDefaults.topAppBarColors().copy(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                navigationIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                actionIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        )
    }
}

@Composable
fun Primary() {
    var selected by remember { mutableIntStateOf(0) }
    PrimaryTabRow(
        modifier = Modifier.fillMaxWidth(),
        selectedTabIndex = selected
    ) {
        (0..2).forEach {
            Tab(
                selected = it == selected,
                text = {
                    if (it == 2)
                        Text("Wider Tab ${it + 1}")
                    else
                        Text("Tab ${it + 1}")
                },
                onClick = { selected = it }
            )
        }
    }
    when (selected) {
        0 -> {
            Text("Page 1")
        }

        1 -> {
            Text("Page 2")
        }

        2 -> {
            Text("Page 3")
        }
    }
}

@Composable
fun Secondary() {
    var selected by remember { mutableIntStateOf(0) }
    SecondaryTabRow(
        modifier = Modifier.fillMaxWidth(),
        selectedTabIndex = selected
    ) {
        (0..2).forEach {
            Tab(
                selected = it == selected,
                text = { Text("Tab ${it + 1}") },
                onClick = { selected = it }
            )
        }
    }
    when (selected) {
        0 -> {
            Text("Page 1")
        }

        1 -> {
            Text("Page 2")
        }

        2 -> {
            Text("Page 3")
        }
    }
}

@Composable
fun PrimaryScrollable() {
    var selected by remember { mutableIntStateOf(0) }
    PrimaryScrollableTabRow(
        selectedTabIndex = selected,
        modifier = Modifier.fillMaxWidth()
    ) {
        (0..2).forEach {
            Tab(
                selected = it == selected,
                text = { Text("Tab ${it + 1}") },
                onClick = { selected = it }
            )
        }
    }
    when (selected) {
        0 -> {
            Text("Page 1")
        }

        1 -> {
            Text("Page 2")
        }

        2 -> {
            Text("Page 3")
        }
    }
}

@Composable
fun SecondaryScrollable() {
    var selected by remember { mutableIntStateOf(0) }
    SecondaryScrollableTabRow(
        selectedTabIndex = selected,
        modifier = Modifier.fillMaxWidth()
    ) {
        (0..2).forEach {
            Tab(
                selected = it == selected,
                text = { Text("Tab ${it + 1}") },
                onClick = { selected = it }
            )
        }
    }
    when (selected) {
        0 -> {
            Text("Page 1")
        }

        1 -> {
            Text("Page 2")
        }

        2 -> {
            Text("Page 3")
        }
    }
}

@Composable
fun PrimaryScrollableEdge() {
    var selected by remember { mutableIntStateOf(0) }
    PrimaryScrollableTabRow(
        selectedTabIndex = selected,
        modifier = Modifier.fillMaxWidth(),
        removeEdgePadding = true
    ) {
        (0..2).forEach {
            Tab(
                selected = it == selected,
                text = { Text("Tab ${it + 1}") },
                onClick = { selected = it }
            )
        }
    }
    when (selected) {
        0 -> {
            Text("Page 1")
        }

        1 -> {
            Text("Page 2")
        }

        2 -> {
            Text("Page 3")
        }
    }
}

@Composable
fun SecondaryScrollableEdge() {
    var selected by remember { mutableIntStateOf(0) }
    SecondaryScrollableTabRow(
        selectedTabIndex = selected,
        modifier = Modifier.fillMaxWidth(),
        removeEdgePadding = true
    ) {
        (0..2).forEach {
            Tab(
                selected = it == selected,
                text = { Text("Tab ${it + 1}") },
                onClick = { selected = it }
            )
        }
    }
    when (selected) {
        0 -> {
            Text("Page 1")
        }

        1 -> {
            Text("Page 2")
        }

        2 -> {
            Text("Page 3")
        }
    }
}