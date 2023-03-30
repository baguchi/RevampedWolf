package baguchan.revampedwolf.api;

import net.minecraft.util.StringRepresentable;

public enum WolfTypes implements StringRepresentable {
	WHITE("white"),
	BLACK("black"),
	BROWN("brown"),
	CHOCO("choco"),
	GRAY("gray");

	public static final StringRepresentable.EnumCodec<WolfTypes> CODEC = StringRepresentable.fromEnum(WolfTypes::values);
	public final String type;

	private WolfTypes(String p_28967_) {
		this.type = p_28967_;
	}

	public String getSerializedName() {
		return this.type;
	}

	public static WolfTypes byType(String p_28977_) {
		return CODEC.byName(p_28977_);
	}
}