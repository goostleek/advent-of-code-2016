package advent.calendar.day4

import static advent.calendar.day2.KeypadState.*

import spock.lang.Specification
import spock.lang.Unroll

public class RoomDecryptorSpecification extends Specification {

    @Unroll
    def "Should decrypt #encryptedName"() {
        when:
            def decryptionResult = RoomDecryptor.decrypt(encryptedName)
        then:
            decryptionResult.sectorId == sectorId
            decryptionResult.isValid == isValid
        where:
            encryptedName                  || sectorId | isValid
            'aaaaa-bbb-z-y-x-123[abxyz]'   || 123      | true
            'a-b-c-d-e-f-g-h-987[abcde]'   || 987      | true
            'not-a-real-room-404[oarel]'   || 404      | true
            'totally-real-room-200[decoy]' || 200      | false
    }

}