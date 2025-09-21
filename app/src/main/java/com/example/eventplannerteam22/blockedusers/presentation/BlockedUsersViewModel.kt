
package com.example.eventplannerteam22.blockedusers.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.blockedusers.data.BlockUserRequest
import com.example.eventplannerteam22.blockedusers.data.UnblockUserRequest
import com.example.eventplannerteam22.blockedusers.domain.BlockedUsersRepository
import com.example.eventplannerteam22.session.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class BlockedUsersViewModel @Inject constructor(
    private val repository: BlockedUsersRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {
    private val _state = MutableStateFlow(BlockedUsersState())
    val state: StateFlow<BlockedUsersState> = _state

    private val userId: Int? get() = sessionRepository.getUserId().takeIf { it != -1 }

    init {
        loadBlockedUsers()
    }

    fun loadBlockedUsers() {
        val blockerId = userId ?: return
        _state.value = _state.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                val users = repository.getBlockedUsers(blockerId)
                _state.value = _state.value.copy(
                    blockedUsers = users.map {
                        BlockedUserUiModel(
                            id = it.blocked.id,
                            name = it.blocked.name,
                            surname = it.blocked.surname,
                            email = it.blocked.email
                        )
                    },
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    fun blockUserByEmail(email: String) {
        val blockerId = userId ?: return
        _state.value = _state.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                val blockedIdResult = repository.getUserIdByEmail(email)
                if (blockedIdResult.isSuccess) {
                    val blockedId = blockedIdResult.getOrNull()!!
                    val result = repository.blockUser(BlockUserRequest(blockerId = blockerId, blockedId = blockedId))
                    if (result.isSuccess) {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = null
                        )
                        loadBlockedUsers()
                        _state.value = _state.value.copy(error = "User successfully blocked")
                    } else {
                        _state.value = _state.value.copy(isLoading = false, error = result.exceptionOrNull()?.message)
                    }
                } else {
                    _state.value = _state.value.copy(isLoading = false, error = "User with this email not found")
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    fun unblockUser(blockedId: Int) {
        val blockerId = userId ?: return
        _state.value = _state.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                val result = repository.unblockUser(UnblockUserRequest(blockerId = blockerId, blockedId = blockedId))
                if (result.isSuccess) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = null
                    )
                    loadBlockedUsers()
                    _state.value = _state.value.copy(error = "User successfully unblocked")
                } else {
                    _state.value = _state.value.copy(isLoading = false, error = result.exceptionOrNull()?.message)
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
        }
    }
}

data class BlockedUsersState(
    val blockedUsers: List<BlockedUserUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
