package dev.dprice.productivity.todo.features.groups.feature.screens.selector

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.groups.data.model.Colour
import dev.dprice.productivity.todo.features.groups.data.model.Group
import dev.dprice.productivity.todo.features.groups.data.model.Icon
import dev.dprice.productivity.todo.features.groups.feature.R
import dev.dprice.productivity.todo.features.groups.feature.screens.selector.model.GroupSelectorAction
import dev.dprice.productivity.todo.features.groups.feature.screens.selector.model.GroupSelectorState
import dev.dprice.productivity.todo.ui.ButtonLayout
import dev.dprice.productivity.todo.ui.components.animated.PulsingLayout
import dev.dprice.productivity.todo.ui.components.scaffold.WavyBackdropScaffold
import dev.dprice.productivity.todo.ui.components.scaffold.WavyScaffoldState
import dev.dprice.productivity.todo.ui.theme.MediumBlue
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme
import dev.dprice.productivity.todo.ui.theme.Yellow
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun GroupSelectorScreen(
    state: GroupSelectorState,
    wavyState: WavyScaffoldState,
    modifier: Modifier = Modifier,
    onAddGroup: () -> Unit,
    onAction: (GroupSelectorAction) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        LaunchedEffect(key1 = Unit) {
            wavyState.animate(
                backDropHeight = maxHeight,
                waveHeight = 0.dp
            )
        }

        WavyBackdropScaffold(
            state = wavyState,
            layoutBeyondConstraints = false,
            backContent = {
                GroupSelectorContent(
                    state = state,
                    onSelect = { onAction(GroupSelectorAction.SelectGroup(it?.id)) },
                    onLongPress = { onAction(GroupSelectorAction.LongPressGroup(it?.id)) },
                    onDelete = { onAction(GroupSelectorAction.DeleteGroups) }
                )
            }
        )

        FloatingActionButton(
            onClick = onAddGroup,
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.BottomEnd),
            backgroundColor = MediumBlue
        ) {
            Icon(
                Icons.Default.Add,
                "Add Group",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun GroupSelectorContent(
    state: GroupSelectorState,
    modifier: Modifier = Modifier,
    onSelect: (Group?) -> Unit,
    onDelete: () -> Unit,
    onLongPress: (Group?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        Box(
            modifier = Modifier
                .height(72.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Groups",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.h3,
            )
            IconButton(
                onClick = onDelete,
                enabled = state.groups.any { it.isSelected },
                modifier = Modifier.padding(8.dp).align(Alignment.CenterEnd)
            ) {
                this@Column.AnimatedVisibility(
                    visible = state.isEditMode,
                    enter = fadeIn() + expandIn(expandFrom = Alignment.Center),
                    exit = fadeOut() + shrinkOut(shrinkTowards = Alignment.Center)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete ${state.groups.count { it.isSelected }} Groups", // todo: Plural string
                    )
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.groups) { (group, count, isSelected) ->
                GroupButton(
                    title = group?.name ?: "All",
                    isSelected = isSelected,
                    taskCount = count,
                    onSelect = { onSelect(group) },
                    colour = group?.colour?.asColour() ?: MediumBlue,
                    icon = group?.icon?.asImageVector() ?: Icons.Default.DoneAll,
                    onLongClick = { group?.let { onLongPress(it) } },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// todo: animate height of first time
// todo: Share animation state from navigation?
@Composable
@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
private fun GroupButton(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    taskCount: Int,
    modifier: Modifier = Modifier,
    colour: Color = MediumBlue,
    contentColour: Color = Color.White,
    onSelect: () -> Unit,
    onLongClick: () -> Unit
) {
    PulsingLayout {
        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(size = 32.dp))
                .combinedClickable(
                    onClick = onSelect,
                    onLongClick = onLongClick
                )
                .then(modifier),
            border = BorderStroke(
                color = if (isSelected) Color.Black else Color.Unspecified,
                width = 3.dp
            ),
            color = colour,
            shape = RoundedCornerShape(size = 32.dp)
        ) {
            Column {
                ButtonLayout(
                    contentColour = contentColour,
                    icon = icon,
                    text = title
                )

                Text(
                    text = pluralStringResource(
                        id = R.plurals.task_count,
                        count = taskCount,
                        taskCount
                    ),
                    color = contentColour.copy(alpha = 0.8f),
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 24.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewGroupSelectorScreen() {
    TodoAppTheme {
        BoxWithConstraints {
            GroupSelectorScreen(
                state = GroupSelectorState(
                    messageFlow = MutableSharedFlow()
                ),
                wavyState = WavyScaffoldState(
                    initialBackDropHeight = maxHeight
                ),
                onAction = { },
                onAddGroup = { }
            )
        }
    }
}

// todo: Need to remove duplication of this
fun Colour.asColour() = when(this) {
    Colour.DEFAULT -> MediumBlue
    Colour.BLUE -> Color(0xFF1879C5)
    Colour.BLUE_LIGHT -> Color(0xFF58B6FF)
    Colour.BLUE_DARK -> Color(0xFF015FA8)
    Colour.PURPLE -> Color(0xFF9C27B0)
    Colour.PURPLE_DARK -> Color(0xFF810896)
    Colour.PINK -> Color(0xFFF177F0)
    Colour.PINK_DARK -> Color(0xFFC26BC1)
    Colour.RED_LIGHT -> Color(0xFFFF4040)
    Colour.RED -> Color(0xFFCA0B0B)
    Colour.RED_DARK -> Color(0xFFA00303)
    Colour.ORANGE_LIGHT -> Color(0xFFFFAE36)
    Colour.ORANGE -> Color(0xFFFF9800)
    Colour.ORANGE_DARK -> Color(0xFFBB6F00)
    Colour.YELLOW_LIGHT -> Color(0xFFFFC03F)
    Colour.YELLOW -> Yellow
    Colour.YELLOW_DARK -> Color(0xFFC98B0F)
    Colour.GREEN_LIGHT -> Color(0xFF57C239)
    Colour.GREEN -> Color(0xFF2BC500)
    Colour.GREEN_DARK -> Color(0xFF228806)
    Colour.CYAN -> Color(0xFF00C587)
    Colour.CYAN_DARK -> Color(0xFF009768)
    Colour.BLACK -> Color(0xFF161616)
    Colour.GREY -> Color(0xFF555555)
}

fun Icon.asImageVector() = when(this) {
    Icon.NONE -> Icons.Filled.Clear
    Icon.HOME -> Icons.Filled.Home
    Icon.STAR -> Icons.Filled.Star
    Icon.FAVOURITE -> Icons.Filled.Favorite
    Icon.TICK -> Icons.Filled.Check
    Icon.BIN -> Icons.Filled.Delete
    Icon.KEY -> Icons.Filled.Key
    Icon.NAVIGATION_ARROW -> Icons.Filled.Assistant
    Icon.ABC -> Icons.Filled.Abc
    Icon.PERSON -> Icons.Filled.Person
    Icon.GROUP -> Icons.Filled.Group
    Icon.WORLD -> Icons.Filled.Public
    Icon.FACE -> Icons.Filled.Face
    Icon.ROCKET -> Icons.Filled.Rocket
    Icon.WINNER -> Icons.Filled.MilitaryTech
    Icon.PET -> Icons.Filled.Pets
    Icon.LEAF -> Icons.Filled.Eco
    Icon.SUN -> Icons.Filled.WbSunny
    Icon.LIGHT_BULB -> Icons.Filled.Lightbulb
    Icon.DIAMOND -> Icons.Filled.Diamond
    Icon.TREES -> Icons.Filled.Forest
    Icon.RABBIT -> Icons.Filled.CrueltyFree
    Icon.VIRUS -> Icons.Filled.Coronavirus
    Icon.MEDICAL -> Icons.Filled.MedicalServices
    Icon.COOKIE -> Icons.Filled.Cookie
    Icon.MOON -> Icons.Filled.Nightlight
    Icon.CLOUD -> Icons.Filled.Cloud
    Icon.TEA -> Icons.Filled.LocalCafe
    Icon.EGG -> Icons.Filled.Egg
    Icon.CELEBRATION -> Icons.Filled.Celebration
    Icon.BIRTHDAY -> Icons.Filled.Cake
    Icon.FINGERPRINT -> Icons.Filled.Fingerprint
    Icon.LOCK -> Icons.Filled.Lock
    Icon.HOURGLASS -> Icons.Filled.HourglassEmpty
    Icon.PIN -> Icons.Filled.Pin
    Icon.BELL -> Icons.Filled.CircleNotifications
    Icon.ALARM -> Icons.Filled.Alarm
    Icon.MAIL -> Icons.Filled.Mail
    Icon.PHONE -> Icons.Filled.Phone
    Icon.MOBILE -> Icons.Filled.Smartphone
    Icon.EDIT -> Icons.Filled.Edit
    Icon.PALETTE -> Icons.Filled.Palette
    Icon.LANDSCAPE -> Icons.Filled.Landscape
    Icon.SHOPPING_CART -> Icons.Filled.ShoppingCart
    Icon.BANK -> Icons.Filled.AccountBalance
    Icon.SAVINGS -> Icons.Filled.Savings
    Icon.BRIEFCASE -> Icons.Filled.Work
    Icon.MAP -> Icons.Filled.Map
    Icon.KNIFE_AND_FORK -> Icons.Filled.Restaurant
    Icon.CHURCH -> Icons.Filled.Church
    Icon.WRENCH -> Icons.Filled.Plumbing
    Icon.MOSQUE -> Icons.Filled.Mosque
    Icon.PLANE -> Icons.Filled.Flight
    Icon.BIKE -> Icons.Filled.PedalBike
    Icon.CAR -> Icons.Filled.DirectionsCar
    Icon.TRAIN -> Icons.Filled.Train
    Icon.SCHOOL -> Icons.Filled.School
    Icon.SCIENCE -> Icons.Filled.Science
    Icon.HIKE -> Icons.Filled.Hiking
    Icon.YOGA -> Icons.Filled.SelfImprovement
    Icon.BURGER -> Icons.Filled.LunchDining
    Icon.RAMEN -> Icons.Filled.RamenDining
    Icon.MUSEUM -> Icons.Filled.Museum
    Icon.CONTROLLER -> Icons.Filled.SportsEsports
}