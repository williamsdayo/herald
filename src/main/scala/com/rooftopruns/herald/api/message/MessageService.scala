package com.rooftopruns.herald.api.message

import com.rooftopruns.herald.api.message.Models.CreateMessage

object MessageService {

  def create(cmd: CreateMessage) = MessageRepository.create(cmd)
}
