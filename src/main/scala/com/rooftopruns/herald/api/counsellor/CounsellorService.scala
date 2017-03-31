package com.rooftopruns.herald.api.counsellor

import com.rooftopruns.herald.api.counsellor.Models.Counsellor
import com.rooftopruns.herald.api.user.Models.Credentials
import slick.Tables.CounsellorsRow

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object CounsellorService {

  def findByToken(token: String): Future[CounsellorsRow] = CounsellorRepository.findByToken(token).map(_.get)

  def fetchAll(): Future[Seq[Counsellor]] = for {
    counsellorRows <- CounsellorRepository.fetchAll()
    counsellors = counsellorRows.map(row => Counsellor(row.id, row.username, row.password, row.email, row.expertise.split(",")))
  } yield counsellors
  
  def authenticate(creds: Credentials): Future[Option[String]] = for {
    counsellorRows <- CounsellorRepository.fetchAll()
    matchingUser = counsellorRows.find(counsellor => counsellor.username == creds.username && counsellor.password == creds.password)
    possibleToken = {
      val token = java.util.UUID.randomUUID().toString
      matchingUser
        .map { counsellor => CounsellorRepository.setToken(counsellor.id, token) }
        .map { _ => token }
    }
  } yield possibleToken
}
