package baguchan.revampedwolf.api;

public interface IHunt {
	void setHuntCooldown(int cooldown);

	int getHuntCooldown();

	boolean isHunted();
}
