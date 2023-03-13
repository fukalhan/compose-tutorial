package com.example.composetutorial.counter

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composetutorial.R
import com.example.composetutorial.ui.theme.DarkPurple
import com.example.composetutorial.ui.theme.LightPurple


@Preview
@Composable
fun CounterScreenPreview() {
    Counter(0,{},{},{},{})
}

@Composable
fun CounterScreen() {
    val viewModel = viewModel<CounterVM>()
    val counterVal: Int by viewModel.counterValue.observeAsState(0)
    Counter(
        counterVal,
        viewModel::decrement,
        viewModel::increment,
        viewModel::resetValue,
        viewModel::setValue
    )
}

@Composable
fun Counter(
    counterVal: Int,
    dec: () -> Unit,
    inc: () -> Unit,
    reset: () -> Unit,
    set: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.counter),
            contentDescription = "Chart Icon",
            modifier = Modifier
                .paddingFromBaseline(top = 80.dp)
                .size(150.dp)
                .clip(RectangleShape)
        )
        
        Text(
            text = "Counter value:",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(24.dp),
        )
        
        DecIncRow(counterVal, dec, inc)

        Spacer(modifier = Modifier.weight(1f))

        SetValueRow(set)
        
        ResetButton {
            reset()
        }
    }
}

@Composable
fun DecIncRow(counterVal: Int, dec: () -> Unit, inc: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        CounterButton(resId = R.drawable.minus, contentDes = "minus", dec)

        Text(
            text = "$counterVal",
            style = MaterialTheme.typography.h3,
        )

        CounterButton(resId = R.drawable.plus, contentDes = "plus", inc)
    }
}

@Composable
fun CounterButton(resId: Int, contentDes: String, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .padding(30.dp)
            .background(LightPurple)
    ) {
        Icon(
            painter = painterResource(id = resId),
            contentDescription = contentDes,
            tint = DarkPurple
        )
    }
}

@Composable
fun SetValueRow(set: (Int) -> Unit) {
    var setterValue by remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        ValueSetter(value = setterValue, onSetterValueChange = { setterValue = it})
        SetButton {
            set(setterValue.toInt())
            setterValue = ""
        }
    }
}

@Composable
fun ValueSetter(value: String, onSetterValueChange: (String) -> Unit) {
    TextField(
        value = value,
        colors = TextFieldDefaults.textFieldColors(
            textColor = DarkPurple,
            backgroundColor = Color.LightGray
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { Text(text = "Value:") },
        modifier = Modifier.width(150.dp),
        onValueChange = onSetterValueChange
    )
}

@Composable
fun SetButton(onButtonClick: () -> Unit) {
    Button(
        onClick = onButtonClick,
        colors = ButtonDefaults.buttonColors(LightPurple)
    ) {
        Text(
            text = "Set",
            color = DarkPurple
        )
    }
}

@Composable
fun ResetButton(reset: () -> Unit) {
    Button(
        onClick = reset,
        modifier = Modifier
            .paddingFromBaseline(bottom = 48.dp)
            .wrapContentHeight()
            .width(200.dp),
        colors = ButtonDefaults.buttonColors(LightPurple),
        elevation = ButtonDefaults.elevation(0.dp)
    ) {
        Text(
            text = "Reset",
            color = DarkPurple
        )
    }
}