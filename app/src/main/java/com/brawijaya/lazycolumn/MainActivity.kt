package com.brawijaya.lazycolumn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.brawijaya.lazycolumn.ui.theme.LazyColumnTheme
import com.brawijaya.lazycolumn.view.UserListScreen
import com.brawijaya.lazycolumn.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: UserViewModel by viewModels { UserViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumnTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserListScreen(viewModel = viewModel)
                }
            }
        }
    }
}