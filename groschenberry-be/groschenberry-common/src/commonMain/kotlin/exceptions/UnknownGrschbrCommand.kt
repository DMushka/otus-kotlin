package com.otus.otuskotlin.groschenberry.common.exceptions

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCommand


class UnknownGrschbrCommand(command: GrschbrCommand) : Throwable("Wrong command $command at mapping toTransport stage")
