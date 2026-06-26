package com.example.variables91

/**
 * Project: Variables91
 * From: com.example.variables91
 * Created by: sebastianperez
 * On: 25/06/26
 * All rights reserved: 2026
 */

val participantes = mutableListOf<Pair<String, Int>>()

fun main() {
    mostrarMenuTorneo()
}

fun mostrarMenuTorneo() {
    while (true) {
        println("""
        ===== SISTEMA DE TORNEOS =====

        1. Registrar participante
        2. Registrar puntos
        3. Consultar participante
        4. Mostrar estadísticas del torneo
        5. Mostrar estadísticas avanzada del torneo
        6. Finalizar programa

        Seleccione una opción:
    """.trimIndent())

        val resp = readln().toInt()
        when(resp) {
            1 -> registrarParticipante()
            2 -> registrarPuntos()
            3 -> consultarParticipante()
            4 -> mostrarEstadisticasTorneo()
            5 -> mostrarEstadisticaAvanzada()
            6 -> break
        }
    }
}
fun registrarParticipante() {
    while (true) {
        println("""
            =========================
            
            Ingresa el nombre de el participante de deseas agregar:
        """.trimIndent())

        val nombre = readln()

        if(nombre.isBlank() || nombre.isEmpty()) {
            println("""
                =========================
            
                Nombre ingresado invalido, volver a intentarlo.
            """.trimIndent())
            continue
        }
        if(participantes.any { it.first == nombre }) {
            println("""
                =========================
                
                Nombre ingresado duplicado, volver a intentarlo.
            """.trimIndent())
            continue

        }

        participantes.add(nombre to 0)
        break
    }
}
fun registrarPuntos() {
    if (!validarParticipantes()) return

    while (true) {
        println("""
            =========================
            
            Ingresa el nombre de el participante de deseas agregarle puntos:
        """.trimIndent())

        val nombre = readln()

        if(nombre.isBlank() || nombre.isEmpty() || !participantes.any { it.first.equals(nombre, ignoreCase = true) }) {
            println("""
                =========================

                Nombre ingresado invalido, volver a intentarlo.
            """.trimIndent())
            continue
        }

        println("""
            =========================
            
            Ingresa la cantidad de puntos para $nombre:
        """.trimIndent())

        val puntos = readln().toIntOrNull()
        if (puntos == null || puntos < 0) {
            println("""
                =========================

                Cantidad de puntos ingresado invalido, volver a intentarlo.
            """.trimIndent())
            continue
        }


        val indice = participantes.indexOfFirst { it.first == nombre }
        val participante = obtenerParticipante(nombre)
        participantes[indice] = participante.first to (participante.second + puntos)
        break
    }
}
fun consultarParticipante() {
    if (!validarParticipantes()) return

    while (true) {
        println(
            """
            =========================

            Ingresa el nombre de el participante de deseas consultar:
        """.trimIndent()
        )

        val nombre = readln()
        if(nombre.isBlank() || nombre.isEmpty() || !participantes.any { it.first.equals(nombre, ignoreCase = true) }) {
            println("""
                =========================

                Nombre ingresado invalido, volver a intentarlo.
            """.trimIndent())
            continue
        }

        val participante = obtenerParticipante(nombre)

        println("""
            =========================

            Participante
            
            Nombre: ${participante.first}
            Puntos: ${participante.second}
            Categoria: ${buscarCategoria(participante.second)}
        """.trimIndent())
        break
    }
}
fun obtenerParticipante(nombre: String): Pair<String, Int> {
    val indice = participantes.indexOfFirst { it.first == nombre }
    return participantes[indice]
}
fun buscarCategoria(total: Int): String {
    return when {
        total > 1000 -> "Leyenda"
        total > 500 -> "Experto"
        total > 200 -> "Competidor"
        else -> "Novato"
    }
}
fun mostrarEstadisticasTorneo() {
    val totalParticipantes = participantes.size
    val totalPuntos = participantes.sumOf { it.second }
    val participanteMaxPts = participantes.maxBy { it.second }
    val participanteMinPts = participantes.minBy { it.second }
    val participanteCategorias = mutableListOf<Pair<String, String>>()

    for (i in 0..<participantes.size) {
        val categoria = buscarCategoria(participantes[i].second)
        participanteCategorias.add(participantes[i].first to categoria)
    }
    val cantLeyenda = participanteCategorias.count { it.second == "Leyenda" }
    val cantExperto = participanteCategorias.count { it.second == "Experto" }
    val cantCompetidor = participanteCategorias.count { it.second == "Competidor" }
    val cantNovato = participanteCategorias.count { it.second == "Novato" }

    println("""
        ===== ESTADISTICAS DEL TORNEO =====
        
        Total de Participantes: $totalParticipantes
        Total de puntos en el torneo: $totalPuntos
        Promedio de puntos en el torneo: ${totalPuntos / totalParticipantes}
        Participante con mas puntos: ${participanteMaxPts.first} con ${participanteMaxPts.second} puntos.
        Participante con menos puntos: ${participanteMinPts.first} con ${participanteMinPts.second} puntos.
        
        Participantes
    """.trimIndent())
        for (i in 0..<participanteCategorias.size) {
            println("${participanteCategorias[i].first} - ${participanteCategorias[i].second}")
        }

    println("""
        
        Numero de participantes por categoria: 
        - Leyenda: $cantLeyenda
        - Experto: $cantExperto
        - Competidor: $cantCompetidor
        - Novato: $cantNovato
        
        =========================  
    """.trimIndent())
}
fun mostrarEstadisticaAvanzada() {
    if (!validarParticipantes()) return

    val totalPuntos = participantes.sumOf { it.second }
    val puntosPromedio = totalPuntos.toDouble() / participantes.size

    val participantesMayorPromedio = participantes.filter { it.second > puntosPromedio }

    println("""
        ===== ESTADISTICAS AVANZADAS DEL TORNEO =====
        Promedio: $puntosPromedio
        Participantes con más puntos que el promedio:
    """.trimIndent())
    participantesMayorPromedio.forEach {
        println("${it.first}: ${it.second} puntos")
    }
}
fun validarParticipantes(): Boolean {
    if (!participantes.isEmpty()) return true
    println("""
            =========================

            No hay participantes registrados.
        """.trimIndent())
    return false
}