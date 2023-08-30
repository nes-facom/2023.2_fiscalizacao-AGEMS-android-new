package com.ufms.nes.core.commons

object Constants {

    const val PASSWORD_INVALID = "A senha deve atender aos seguintes critérios:\n" +
            "        \\n 1. Comprimento mínimo de 8 caracteres.\n" +
            "        \\n 2. Contém pelo menos uma letra maiúscula.\n" +
            "        \\n 3. Contém pelo menos uma letra minúscula.\n" +
            "        \\n 4. Contém pelo menos um dígito.\n" +
            "        \\n 5. Contém pelo menos um caractere especial"

    const val EMPTY_FIELDS = "Todos os campos precisam ser preenchidos"

    const val PASSWORD_NOT_MATCHING = "A confirmação deve ser igual à senha"

    const val EMAIL_INVALID = "E-mail inválido"

    const val ERROR_MESSAGE = "Desculpe, ocorreu algum erro"
}
