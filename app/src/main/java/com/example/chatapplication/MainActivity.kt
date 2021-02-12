package com.example.chatapplication

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Providers
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.node.Ref
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.compositionContext
import androidx.compose.ui.platform.findViewTreeCompositionContext
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import com.example.chatapplication.component.ChatScaffOld
import com.example.chatapplication.conversation.BackPressHandler
import com.example.chatapplication.conversation.LocalBackPressedDispatcher
import com.example.chatapplication.databinding.ActivityMainBinding
import com.example.chatapplication.ui.theme.ChatApplicationTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets


class MainActivity : AppCompatActivity() {

    private val viewModel : MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets(consumeWindowInsets = false) {
                Providers(LocalBackPressedDispatcher provides this.onBackPressedDispatcher) {
                    val scaffoldState = rememberScaffoldState()

                    val openDrawerEvent = viewModel.drawerShouldBeOpened.observeAsState()
                    if(openDrawerEvent.value == true) {
                        scaffoldState.drawerState.open {
                            viewModel.resetOpenDrawerAction()
                        }
                    }

                    if(scaffoldState.drawerState.isOpen) {
                        BackPressHandler(onBackPressed = { scaffoldState.drawerState.close() })
                    }

                    ChatScaffOld(
                        scaffoldState = scaffoldState,
                        onChatClicked = {

                        },
                        onProfileClicked = {

                        }
                    ) {
                        val bindingRef = remember { Ref<ViewBinding>() }
                        val currentView = LocalView.current

                        AndroidViewBinding({ inflater, parent, attachToParent ->
                            if (bindingRef.value == null) {
                                val binding =
                                    ActivityMainBinding.inflate(inflater, parent, attachToParent)
                                bindingRef.value = binding
                                binding.root.compositionContext =
                                    currentView.findViewTreeCompositionContext()
                            }
                            bindingRef.value as ViewBinding
                        })

                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp() || super.onNavigateUp()
    }
}