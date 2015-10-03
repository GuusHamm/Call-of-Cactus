package com.netgames.coc.game;

public class AICharacter extends Player {

	private boolean followPlayer = true;

	public boolean isFollowPlayer() {
		return this.followPlayer;
	}

	public void setFollowPlayer(boolean followPlayer) {
		this.followPlayer = followPlayer;
	}

	public AICharacter() {
		// TODO - implement AICharacter.AICharacter
		throw new UnsupportedOperationException();
	}

}