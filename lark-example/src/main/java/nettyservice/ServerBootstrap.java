package nettyservice;

/**
 *
 *
 * @Date: 2022/1/12 21:38
 * @Description:
 * @Author: LarkMidTable
 **/
public class ServerBootstrap {
	public static void main(String[] args) {
		UserServiceImpl.startServer("localhost", 8990);
	}
}
