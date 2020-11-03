package id.hanifsr.gamedb.util

import id.hanifsr.gamedb.data.model.Game

interface OnItemClickCallback {
	fun onItemClicked(game: Game)
}