package com.practicum.playlistmaker3.domain.getCreateTime

class GetCreateTimeUC(private val trackCreateTime: TrackCreateTime) {

      fun getCurrentTime(): Long{
          return trackCreateTime.getCurrentTime()
      }
}