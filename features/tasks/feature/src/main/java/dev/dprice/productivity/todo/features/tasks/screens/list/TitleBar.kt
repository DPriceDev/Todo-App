package dev.dprice.productivity.todo.features.tasks.screens.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.groups.data.model.Colour
import dev.dprice.productivity.todo.features.groups.data.model.Icon
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TaskFilter
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TaskListAction
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TitleBarState
import dev.dprice.productivity.todo.ui.ButtonLayout
import dev.dprice.productivity.todo.ui.components.SearchableTitleBar
import dev.dprice.productivity.todo.ui.components.Shimmer
import dev.dprice.productivity.todo.ui.components.SlideSelector
import dev.dprice.productivity.todo.ui.components.buttons.PulsingButton
import dev.dprice.productivity.todo.ui.theme.DarkBlue
import dev.dprice.productivity.todo.ui.theme.MediumBlue
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme
import dev.dprice.productivity.todo.ui.theme.Yellow

@Composable
fun TitleBar(
    state: TitleBarState,
    openGroupSelector: () -> Unit,
    onAction: (TaskListAction) -> Unit
) {
    Column {
        SearchableTitleBar(
            entry = state.searchEntry,
            isSearchShown = state.isSearchShown,
            onTextChange = { onAction(TaskListAction.UpdateSearchText(it)) },
            onFocusChange = { onAction(TaskListAction.UpdateSearchFocus(it)) },
            onSearchClick = { onAction(TaskListAction.SearchButtonClicked) }
        )

        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            PulsingButton(
                modifier = Modifier.weight(1f),
                backgroundColour = state.currentGroup?.colour?.asColour() ?: DarkBlue,
                onClick = openGroupSelector
            ) {
                Shimmer {
                    ButtonLayout(
                        contentColour = Color.White,
                        icon = state.currentGroup?.icon?.asImageVector() ?: Icons.Default.DoneAll,
                        text = state.currentGroup?.name ?: "All"
                    )
                }
            }

            PulsingButton(
                modifier = Modifier.weight(1f),
                backgroundColour = DarkBlue,
                onClick = { /* todo: Open Date Range selector */ }
            ) {
                ButtonLayout(
                    icon = Icons.Default.DateRange,
                    contentColour = Color.White,
                    text = "All"
                )
            }
        }

        Spacer(modifier = Modifier.heightIn(8.dp))

        // Task filters
        SlideSelector(
            TaskFilter.values().map { stringResource(id = it.displayNameId) },
            selected = state.filter.ordinal,
            onSelected = { onAction(TaskListAction.UpdateFilter(TaskFilter.values()[it])) },
            selectedColor = DarkBlue,
            selectedContentColor = Color.White,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        //DateSelector()
    }
}

@Composable
private fun DateSelector() {
    Row(
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(imageVector = Icons.Default.ArrowLeft, contentDescription = null)
        Text(
            text = "Today",
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4
        )
        Icon(imageVector = Icons.Default.ArrowRight, contentDescription = null)
    }
}

@Preview
@Composable
private fun PreviewTitleBar() {
    TodoAppTheme {
        TitleBar(
            state = TitleBarState(),
            openGroupSelector = { /*TODO*/ },
            onAction = { }
        )
    }
}


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