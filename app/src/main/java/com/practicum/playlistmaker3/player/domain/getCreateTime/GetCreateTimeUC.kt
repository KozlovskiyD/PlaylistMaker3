package com.practicum.playlistmaker3.player.domain.getCreateTime

class GetCreateTimeUC(private val trackCreateTime: TrackCreateTime) {

      fun getCurrentTime(durationOrCurrent: Boolean): Long{
          return trackCreateTime.getCurrentTime(durationOrCurrent)
      }
}