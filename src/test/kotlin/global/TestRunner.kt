package global

import com.WiseSayingApp
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.PrintStream

object TestRunner {
    // 원래의 표준 입력과 출력을 저장하여, 테스트 후 복원하기 위한 변수들입니다.
    private val originalIn: InputStream = System.`in`
    private val originalOut: PrintStream = System.out

    // 테스트 실행 함수. 미리 작성된 입력을 주입하고, 앱 실행 결과를 문자열로 반환합니다.
    fun run(input: String): String {
        // 입력 문자열을 trimIndent()를 사용해 포맷팅하고, 마지막에 "종료" 명령어를 추가합니다.
        val formattedInput = input.trimIndent().plus("\n종료")

        // ByteArrayOutputStream을 사용하여 출력 스트림을 캡쳐합니다.
        return ByteArrayOutputStream().use { outputStream ->
            // PrintStream으로 캡쳐 스트림을 생성합니다.
            PrintStream(outputStream).use { printStream ->
                try {
                    // 가짜 입력 스트림을 생성하고, 표준 입력(System.in)을 재지정합니다.
                    System.setIn(
                        ByteArrayInputStream(
                            formattedInput.toByteArray()
                        )
                    )
                    // 표준 출력(System.out)을 가짜 출력 스트림(PrintStream)으로 재지정합니다.
                    System.setOut(printStream)

                    // WiseSayingApp을 실행합니다.
                    WiseSayingApp().run()
                } finally {
                    // 앱 실행 후, 반드시 원래의 표준 입출력 스트림으로 복원합니다.
                    System.setIn(originalIn)
                    System.setOut(originalOut)
                }
            }
            // 캡쳐된 출력 스트림의 내용을 문자열로 변환하여 반환합니다.
            outputStream
                .toString()
                .trim()
                .replace("\r\n", "\n") // 윈도우와 유닉스 개행 문자 차이 표준화
        }
    }
}
