package model;

public enum OperationType {
	DEPIOSIT("Deposit"), WITHDRAW("Withdraw");

	private final String label;

	OperationType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
