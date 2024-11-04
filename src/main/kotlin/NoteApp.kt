import java.util.*
import kotlin.system.exitProcess

class NoteApp {
    private val archives = mutableListOf<Archive>()
    private val scanner = Scanner(System.`in`)

    fun start() {
        while (true) {
            println("Выбор архива:")
            println("1. Показать архивы")
            println("2. Создать архив")
            println("0. Выход")
            when (scanner.nextLine()) {
                "1" -> showArchives()
                "2" -> createArchive()
                "0" -> exitProcess(0)
                else -> println("Некорректный ввод")
            }
        }
    }

    private fun showArchives() {
        if (archives.isEmpty()) {
            println("Нет архивов. Создайте новый архив.")
            return
        }

        while (true) {
            archives.forEachIndexed { index, archive -> println("${index + 1}. $archive") }
            println("Выберите архив для просмотра заметок (введите номер) или нажмите 0 для возврата.")
            val input = scanner.nextLine()

            if (input == "0") {
                start()
                return
            }

            val index = input.toIntOrNull()?.minus(1) ?: -1
            if (index in archives.indices) {
                showNotes(archives[index])
                return
            } else {
                println("Некорректный ввод, попробуйте еще раз.")
            }
        }
    }


    private fun showNotes(archive: Archive) {
        while (true) {
            println("Заметки в архиве: ${archive.name}")
            val notes = archive.getNotes()

            if (notes.isEmpty()) {
                println("Нет заметок. Создайте новую заметку.")
            }

            println("1. Создать заметку")

            if (notes.isNotEmpty()) {
                println("2. Просмотреть заметку")
            }

            println("0. Вернуться в меню архивов")

            when (scanner.nextLine()) {
                "1" -> createNote(archive)
                "2" -> {
                    while (true) {
                        if (notes.isEmpty()) {
                            println("Нет заметок для просмотра.")
                            break
                        }
                        println("Список заметок: ")
                        notes.forEachIndexed { index, note -> println("${index + 1}. ${note.title}") }
                        println("Введите номер заметки для просмотра или 0 для выхода:")

                        val noteInput = scanner.nextLine()
                        if (noteInput == "0") {
                            break
                        }

                        val noteIndex = noteInput.toIntOrNull()?.minus(1) ?: -1
                        if (noteIndex in notes.indices) {
                            viewNote(notes[noteIndex])
                            break
                        } else {
                            println("Некорректный ввод, попробуйте еще раз.")
                        }
                    }
                }
                "0" -> showArchives()
                else -> println("Некорректный ввод")
            }
        }
    }



    private fun createArchive() {
        var archiveName: String

        do {
            println("Введите имя архива:")
            archiveName = scanner.nextLine()
            if (archiveName.isEmpty()) {
                println("Имя архива не может быть пустым. Пожалуйста, введите имя заново.")
            }
        } while (archiveName.isEmpty())

        archives.add(Archive(archiveName))
        println("Архив '$archiveName' создан.")
    }

    private fun createNote(archive: Archive) {
        var noteTitle: String
        var noteContent: String
        do {
            println("Введите заголовок заметки:")
            noteTitle = scanner.nextLine()
            if (noteTitle.isEmpty()) {
                println("Заголовок не может быть пустым. Пожалуйста, введите заголовок заново.")
            }
        } while (noteTitle.isEmpty())

        do {
            println("Введите содержание заметки:")
            noteContent = scanner.nextLine()
            if (noteContent.isEmpty()) {
                println("Содержание не может быть пустым. Пожалуйста, введите содержание заново.")
            }
        } while (noteContent.isEmpty())
        archive.addNote(Note(noteTitle, noteContent))
        println("Заметка '$noteTitle' добавлена.")
    }

    private fun viewNote(note: Note) {
        println("Заголовок заметки: ${note.title}")
        println("Содержание заметки: ${note.content}")
    }
}