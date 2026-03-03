package com.m3.rajat.piyush.studymatealpha.domain.usecase.notice

import com.m3.rajat.piyush.studymatealpha.core.util.Resource
import com.m3.rajat.piyush.studymatealpha.data.local.entity.NoticeEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.NoticeRepository
import javax.inject.Inject

class GetNoticesUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
) {
    suspend operator fun invoke(): Resource<List<NoticeEntity>> {
        return try {
            val notices = noticeRepository.getAll()
            Resource.Success(notices)
        } catch (e: Exception) {
            Resource.Error("Failed to load notices: ${e.message}", e)
        }
    }
}
