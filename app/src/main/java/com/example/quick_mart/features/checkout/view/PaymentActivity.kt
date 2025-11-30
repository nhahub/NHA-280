package com.example.quick_mart.features.checkout.view

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current

    // Fields
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var cardHolder by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    // Validation error states
    var nameError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }
    var addressError by remember { mutableStateOf(false) }
    var cardNumberError by remember { mutableStateOf(false) }
    var cardHolderError by remember { mutableStateOf(false) }
    var expiryError by remember { mutableStateOf(false) }
    var cvvError by remember { mutableStateOf(false) }

    // Validation Function
    fun validate(): Boolean {
        nameError = name.isBlank()
        phoneError = phoneNumber.length != 11
        addressError = address.isBlank()
        cardNumberError = cardNumber.length != 16
        cardHolderError = cardHolder.isBlank()
        expiryError = !expiryDate.matches(Regex("^(0[1-9]|1[0-2])/[0-9]{2}\$"))
        cvvError = cvv.length != 3

        return !(nameError || phoneError || addressError ||
                cardNumberError || cardHolderError ||
                expiryError || cvvError)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Payment",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier.statusBarsPadding()
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    if (validate()) {
                        val intent = Intent(context, SuccessPaymentActivity::class.java)
                        context.startActivity(intent)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(70.dp)
                    .navigationBarsPadding()
            ) {
                Text(
                    "Confirm",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(46.dp))
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // Reusable Field function
            @Composable
            fun FieldLabel(text: String) {
                Text(
                    text,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.sp
                )
            }

            // Name Field
            FieldLabel("Name *")
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    nameError = false
                },
                isError = nameError,
                placeholder = { Text("Enter your name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                )
            )
            if (nameError) Text("Name is required", color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(10.dp))

            // Phone Number
            FieldLabel("Phone Number *")
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                    phoneError = false
                },
                isError = phoneError,
                placeholder = { Text("Enter phone number") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            if (phoneError) Text("Phone number must be 11 digits", color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(10.dp))

            // Address
            FieldLabel("Address *")
            OutlinedTextField(
                value = address,
                onValueChange = {
                    address = it
                    addressError = false
                },
                isError = addressError,
                placeholder = { Text("Enter your address") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            if (addressError) Text("Address is required", color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(10.dp))

            // Card Number
            FieldLabel("Card Number *")
            OutlinedTextField(
                value = cardNumber,
                onValueChange = {
                    cardNumber = it.filter { it.isDigit() }
                    cardNumberError = false
                },
                isError = cardNumberError,
                placeholder = { Text("Enter 16-digit card number") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            if (cardNumberError) Text("Card number must be 16 digits", color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(10.dp))

            // Card Holder
            FieldLabel("Card Holder Name *")
            OutlinedTextField(
                value = cardHolder,
                onValueChange = {
                    cardHolder = it
                    cardHolderError = false
                },
                isError = cardHolderError,
                placeholder = { Text("Name on card") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            if (cardHolderError) Text("Card holder name is required", color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(10.dp))

            // Row: Expiry + CVV
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    FieldLabel("Expiry Date *")
                    OutlinedTextField(
                        value = expiryDate,
                        onValueChange = {
                            expiryDate = it
                            expiryError = false
                        },
                        isError = expiryError,
                        placeholder = { Text("MM/YY") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (expiryError) Text("Invalid format (MM/YY)", color = MaterialTheme.colorScheme.error)
                }

                Column(modifier = Modifier.weight(1f)) {
                    FieldLabel("CVV *")
                    OutlinedTextField(
                        value = cvv,
                        onValueChange = {
                            cvv = it.filter { it.isDigit() }
                            cvvError = false
                        },
                        isError = cvvError,
                        placeholder = { Text("123") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (cvvError) Text("CVV must be 3 digits", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
