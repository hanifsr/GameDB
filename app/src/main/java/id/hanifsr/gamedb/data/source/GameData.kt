package id.hanifsr.gamedb.data.source

import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.data.model.Game

object GameData {
    private val title = arrayOf(
		"Horizon Zero Dawn",
		"The Witcher 3: Wild Hunt",
		"Bioshock Infinite",
		"Assassin's Creed: Odyssey",
		"Dota 2"
	)

    private val genre = arrayOf(
		"Role-playing (RPG), Shooter",
		"Adventure, Role-playing (RPG)",
		"Adventure, Shooter",
		"Adventure, Role-playing (RPG)",
		"MOBA"
	)

    private val poster = intArrayOf(
		R.drawable.hzd_poster,
		R.drawable.witcher3_poster,
		R.drawable.bioshock_infinite_poster,
		R.drawable.ac_odyssey_poster,
		R.drawable.dota2_poster
	)

    val data: ArrayList<Game>
        get() {
            val list = arrayListOf<Game>()
            for (position in title.indices) {
                val game = Game()
                game.title = title[position]
                game.genre = genre[position]
                game.poster = poster[position]
                list.add(game)
            }

            return list
        }
}