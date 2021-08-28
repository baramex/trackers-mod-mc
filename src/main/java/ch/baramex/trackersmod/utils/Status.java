package ch.baramex.trackersmod.utils;

import java.util.HashMap;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class Status {
	
	public static enum TrackStatus {
		NO_STATE(0),
		TRACKING(1),
		ERROR_SERVER(2),
		READY(3),
		PLAYER_NOT_FOUND(4),
		PLAYER_IS_TOO_FAR(5),
		TRACKING_FAILED(6),
		SERVER_NOT_FOUND(7),
		NOT_ENOUGH_ENERGY(8),
		NOT_ENOUGH_ANTENNA(9),
		NOT_ENOUGH_ENERGY_OR_ANTENNA(10);
		
		private int val;
		TrackStatus(int val) {
			this.val = val;
		}
		
		public int getValue() {
			return this.val;
		}
		
		public ITextComponent getTextComponent() {
			return new TextComponentTranslation("status." + Reference.MODID + "." + this.name());
		}
		
		static {
			createMapOfValAndName();
		}
		
		private static HashMap<Integer, TrackStatus> map;
		
		private static void createMapOfValAndName() {
			map = new HashMap<Integer, TrackStatus>();
	        for (TrackStatus b : TrackStatus.values()) {
	             map.put(b.getValue(),b);
	        }
		}
		
		public static TrackStatus getEnumValue(int value) {
			return map.get(value);
		}
	};
}
