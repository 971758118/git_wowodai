package com.example.common;

import java.util.TimerTask;

public class ExitTimerTask extends TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ExitHelper.setIsExit(false);
	}

}
