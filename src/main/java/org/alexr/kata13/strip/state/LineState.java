package org.alexr.kata13.strip.state;

public final class LineState {
  private final String input;
  private final int pos;
  private final StringBuilder output;
  public final boolean inBlock;

  private LineState(String input, int pos, StringBuilder output, boolean inBlock) {
    this.input = input;
    this.pos = pos;
    this.output = output;
    this.inBlock = inBlock;
  }

  public static LineState fresh(String input, boolean inBlock) {
    return new LineState(input, 0, new StringBuilder(), inBlock);
  }

  public LineState sw() {
    return new LineState(input, pos, output, !inBlock);
  }

  public LineState moveTo(int posTo) {
    return new LineState(input, posTo, output, inBlock);
  }

  public LineState shift(int delta) {
    return moveTo(pos + delta);
  }

  public LineState saveTo(int posTo) {
    String part = input.substring(pos, posTo);
    output.append(part);
    return moveTo(posTo);
  }

  public LineState skipRest() {
    return moveTo(input.length());
  }

  public LineState saveRest() {
    return saveTo(input.length());
  }

  public int find(String sub) {
    return input.indexOf(sub, pos);
  }

  public boolean done() {
    return pos >= input.length();
  }

  public String result() {
    return output.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    if (!(o instanceof LineState)) return false;
    LineState that = (LineState) o;
    return this.pos == that.pos
        && this.inBlock == that.inBlock
        && this.input.equals(that.input)
        && this.result().equals(that.result());
  }
}
