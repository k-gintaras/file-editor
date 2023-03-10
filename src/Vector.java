import java.util.Arrays;
/**
 * The MIT License (MIT) Copyright (c)
 * 
 * <2016><Gintaras Koncevicius>(@author Ubaby)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class Vector {
    public double[] values;
    public String nominalLabel;
    public double numericLabel;
    private int closest = 0;
    private int furthest = 0;
    private boolean predicted=false;
    private int predicts=0;

    public int getPredicts() {
        return predicts;
    }

    public boolean isPredicted() {
        return predicted;
    }

    public void setPredicted(boolean predicted) {
	predicts++;
        this.predicted = predicted;
    }

    public Vector(double[] vector) {
	this.values = new double[vector.length];
	for (int i = 0; i < vector.length; i++) {
	    this.values[i] = vector[i];
	}
    }

    public Vector(double[] vector, double label) {
	this.values = new double[vector.length];
	for (int i = 0; i < vector.length; i++) {
	    this.values[i] = vector[i];
	}
	this.numericLabel = label;
    }
    
    public Vector(double[] vector, String label) {
	this.values = new double[vector.length];
	for (int i = 0; i < vector.length; i++) {
	    this.values[i] = vector[i];
	}
	this.nominalLabel = label;
    }

    public int getFurthest() {
	return furthest;
    }

    public void setFurthest() {
	this.furthest++;
    }

    public int getClosest() {
	return closest;
    }

    public void setClosest() {
	this.closest++;
    }

    public void resetClosest() {
	this.closest = 0;
    }

    public void resetFurthest() {
	this.furthest = 0;
    }

    public String getNominalLabel() {
        return nominalLabel;
    }

    public void setNominalLabel(String nominalLabel) {
        this.nominalLabel = nominalLabel;
    }

    public double getNumericLabel() {
        return numericLabel;
    }

    public void setNumericLabel(double numericLabel) {
        this.numericLabel = numericLabel;
    }

    public int hashCode() {
	final int prime = 31;
	int result = 67;
	long temp;
	temp = Double.doubleToLongBits(numericLabel);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + closest;
	result = prime * result + Arrays.hashCode(values);
	return result;
    }
}
