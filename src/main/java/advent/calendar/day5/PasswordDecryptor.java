package advent.calendar.day5;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import lombok.SneakyThrows;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.bind.DatatypeConverter;

public class PasswordDecryptor {

    @SneakyThrows
    public static String decrypt(String encryptedPassword) {
        return Stream.iterate(0, i -> ++i).parallel()
                .map(String::valueOf)
                .map(encryptedPassword::concat)
                .map(String::getBytes)
                .map(Hashing.md5()::hashBytes)
                .map(HashCode::asBytes)
                .map(DatatypeConverter::printHexBinary)
                .filter(hash -> hash.startsWith("00000"))
                .map(hash -> hash.charAt(5))
                .map(String::valueOf)
                .limit(8)
                .collect(Collectors.joining())
                .toLowerCase();
    }

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println(PasswordDecryptor.decrypt("ugkcyxxp"));
    }

}
