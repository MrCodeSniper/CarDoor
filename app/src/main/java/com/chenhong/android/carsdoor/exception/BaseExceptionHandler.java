package com.chenhong.android.carsdoor.exception;

//����δ�����쳣����
public abstract class BaseExceptionHandler implements
		Thread.UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		// TODO Auto-generated method stub

		if (handleException(e)) {
			// ������� ����3S
			try {
				Thread.sleep(3000);

			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);// ��ֹJVM���� �����0���Ƿ����˳�

		}

	}

	public abstract boolean handleException(Throwable e);

}
