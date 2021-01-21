package ru.pb.global.tasks;


import ru.pb.global.models.Room;

/**
 * @autor: Grizly, Felixx
 * Date: 15.01.14
 * Time: 19:09
 */
public class RoomTask implements Runnable {
	private Room room;
	private int channel;
	private IRoomTask event = null;

	public RoomTask(Room room, IRoomTask event, int channel) {
		this.room = room;
		this.event = event;
		this.channel = channel;
	}

	@Override
	public void run() {
		if (event != null)
			event.run(channel, room);
	}
}
