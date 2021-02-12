package com.example.chatapplication.conversation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.runtime.Providers
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.chatapplication.MainViewModel
import com.example.chatapplication.R
import com.example.chatapplication.ui.theme.ChatApplicationTheme
import com.example.chatapplication.utils.exampleUiState
import dev.chrisbanes.accompanist.insets.ExperimentalAnimatedInsets
import dev.chrisbanes.accompanist.insets.LocalWindowInsets
import dev.chrisbanes.accompanist.insets.ViewWindowInsetObserver
import dev.chrisbanes.accompanist.insets.navigationBarsPadding

class chatFragment : Fragment() {
    private val activityMainViewModel : MainViewModel by activityViewModels<MainViewModel>()

    @OptIn(ExperimentalAnimatedInsets::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)

        val windowInsets = ViewWindowInsetObserver(this)
            .start(windowInsetsAnimationsEnabled = true)

        setContent {
            Providers(
                LocalBackPressedDispatcher provides requireActivity().onBackPressedDispatcher,
                LocalWindowInsets provides windowInsets
            ) {
                ChatApplicationTheme {
                    ChatContent(
                        uiState = exampleUiState,
                        navigateToProfile = { user ->
                            val bundle = bundleOf("userId" to user)
                            findNavController().navigate(
                                R.id.nav_graph, // TODO
                                bundle
                            )
                        },
                        onNavIconPressed = { activityMainViewModel.openDrawer() },
                        modifier = Modifier.navigationBarsPadding(bottom = false)
                    )
                }
            }
        }


    }
}