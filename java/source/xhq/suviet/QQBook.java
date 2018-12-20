/*
 * Enum QQBook
 * Ver0.0.1
 * Feature: 
 */

package xhq.suviet;

public enum QQBook {
	shm("924579723"),
	sjx("1774793672"),
	nhx("2654641069"),
	njh("891336007"),
	xhq("994251534"),
	ycx("amxelc@qq.com");
	
	private final String qq_;
	QQBook(String qq) {
		qq_ = qq;
	}
	
	public String getQQ() {
		return qq_;
	}
}
