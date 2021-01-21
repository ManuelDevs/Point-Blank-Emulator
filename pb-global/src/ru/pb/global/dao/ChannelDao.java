package ru.pb.global.dao;

import ru.pb.global.models.Channel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Пользователь
 * Date: 20.01.14
 * Time: 0:52
 * To change this template use File | Settings | File Templates.
 */
public interface ChannelDao {
	List<Channel> getAllChannelByServer(int serverId);
}
