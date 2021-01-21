package ru.pb.global.tasks;

import ru.pb.global.models.Room;

public interface IRoomTask {
	public void run(int channel, Room room);
}
