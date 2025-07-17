package com.otus.otuskotlin.groschenberry.biz.validation

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCommand
import validation.validationLockCorrect
import validation.validationLockEmpty
import validation.validationLockFormat
import validation.validationLockTrim
import kotlin.test.Test

class BizValidationUpdateTest: BaseBizValidationTest() {
    override val command = GrschbrCommand.UPDATE

    @Test fun correctTitle() = validationTitleCorrect(command, processor)
    @Test fun trimTitle() = validationTitleTrim(command, processor)
    @Test fun emptyTitle() = validationTitleEmpty(command, processor)
    @Test fun badSymbolsTitle() = validationTitleSymbols(command, processor)

    @Test fun correctDescription() = validationDescriptionCorrect(command, processor)
    @Test fun trimDescription() = validationDescriptionTrim(command, processor)
    @Test fun emptyDescription() = validationDescriptionEmpty(command, processor)
    @Test fun badSymbolsDescription() = validationDescriptionSymbols(command, processor)

    @Test fun correctMaterial() = validationMaterialCorrect(command, processor)
    @Test fun trimMaterial() = validationMaterialTrim(command, processor)
    @Test fun emptyMaterial() = validationMaterialEmpty(command, processor)
    @Test fun badSymbolsMaterial() = validationMaterialSymbols(command, processor)

    @Test fun correctMint() = validationMintCorrect(command, processor)
    @Test fun trimMint() = validationMintTrim(command, processor)
    @Test fun badSymbolsMint() = validationMintSymbols(command, processor)

    @Test fun correctCIBId() = validationCIBIdCorrect(command, processor)
    @Test fun trimCIBId() = validationCIBIdTrim(command, processor)
    @Test fun emptyCIBId() = validationCIBIdEmpty(command, processor)
    @Test fun badSymbolsCIBId() = validationCIBIdFormat(command, processor)

    @Test fun correctDiameter() = validationDiameterCorrect(command, processor)
    @Test fun negativeDiameter() = validationDiameterNegative(command, processor)
    @Test fun tooBigDiameter() = validationDiameterTooBig(command, processor)

    @Test fun correctCopies() = validationCopiesCorrect(command, processor)
    @Test fun negativeCopies() = validationCopiesNegative(command, processor)
    @Test fun tooBigCopies() = validationCopiesTooBig(command, processor)

    @Test fun correctStartYear() = validationStartYearCorrect(command, processor)
    @Test fun trimStartYear() = validationStartYearTrim(command, processor)
    @Test fun noneNumericStartYear() = validationStartYearNoneNumeric(command, processor)
    @Test fun invalidLengthStartYear() = validationStartYearInvalidLength(command, processor)
    @Test fun inFutureStartYear() = validationStartYearInFuture(command, processor)
    @Test fun inPastStartYear() = validationStartYearInPast(command, processor)

    @Test fun correctStopYear() = validationStopYearCorrect(command, processor)
    @Test fun trimStopYear() = validationStopYearTrim(command, processor)
    @Test fun noneNumericStopYear() = validationStopYearNoneNumeric(command, processor)
    @Test fun invalidLengthStopYear() = validationStopYearInvalidLength(command, processor)
    @Test fun inPastStopYear() = validationStopYearInPast(command, processor)

    @Test fun correctIssueYear() = validationIssueYearCorrect(command, processor)
    @Test fun trimIssueYear() = validationIssueYearTrim(command, processor)
    @Test fun noneNumericIssueYear() = validationStartYearNoneNumeric(command, processor)
    @Test fun invalidLengthIssueYear() = validationIssueYearInvalidLength(command, processor)
    @Test fun inFutureIssueYear() = validationIssueYearInFuture(command, processor)
    @Test fun inPastIssueYear() = validationIssueYearInPast(command, processor)

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)

    @Test fun correctLock() = validationLockCorrect(command, processor)
    @Test fun trimLock() = validationLockTrim(command, processor)
    @Test fun emptyLock() = validationLockEmpty(command, processor)
    @Test fun badFormatLock() = validationLockFormat(command, processor)

}
