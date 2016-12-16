package advent.calendar.day5;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import lombok.SneakyThrows;
import lombok.val;

import java.util.HashSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.bind.DatatypeConverter;

public class PasswordDecryptor {

    public static String decrypt(String encryptedPassword) {
        return Stream.iterate(0, i -> ++i).parallel()
                .map(String::valueOf)
                .map(encryptedPassword::concat)
                .map(String::getBytes)
                .map(Hashing.md5()::hashBytes)
                .map(HashCode::asBytes)
                .map(DatatypeConverter::printHexBinary)
                .filter(hash -> hash.startsWith("00000"))
                .filter(withValidPosition())
                .map(hash -> hash.substring(5, 7))
                .limit(8)
                .sorted()
                .map(s -> s.charAt(1))
                .map(String::valueOf)
                .collect(Collectors.joining())
                .toLowerCase();
    }

    private static Predicate<String> withValidPosition() {
        val usedPositions = new HashSet<Integer>();
        return hash -> {
            boolean isValid = Character.isDigit(hash.charAt(5));
            if (isValid) {
                val pos = Integer.parseInt(Character.toString(hash.charAt(5)));
                isValid = pos >= 0 && pos <= 7 && usedPositions.add(pos);
            }
            return isValid;
        };
    }

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println(PasswordDecryptor.decrypt("ugkcyxxp"));
    }

}
