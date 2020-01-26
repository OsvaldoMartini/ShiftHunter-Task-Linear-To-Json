package com.shifthunter.tasks;


public class TVertice {
	int xPos;
	int yPos;
	String Vert;
	String[] Values;
	int Idx_Ref;
	TVisit TpVis;
	int Custo;
	int OrdIni;
	int OrdFim;

	public TVertice() {
	}

	enum TVisit {
		White, Gray, Black
	}

	public TVertice(int xPos, int yPos, String vert, String[] values, int idx_Ref, TVisit tpVis, int custo, int ordIni,
			int ordFim) {
		super();
		this.xPos = xPos;
		this.yPos = yPos;
		Vert = vert;
		Values = values;
		Idx_Ref = idx_Ref;
		TpVis = tpVis;
		Custo = custo;
		OrdIni = ordIni;
		OrdFim = ordFim;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public String getVert() {
		return Vert;
	}

	public void setVert(String vert) {
		Vert = vert;
	}

	public String[] getValues() {
		return Values;
	}

	public void setValues(String[] values) {
		Values = values;
	}

	public int getIdx_Ref() {
		return Idx_Ref;
	}

	public void setIdx_Ref(int idx_Ref) {
		Idx_Ref = idx_Ref;
	}

	public TVisit getTpVis() {
		return TpVis;
	}

	public void setTpVis(TVisit tpVis) {
		TpVis = tpVis;
	}

	public int getCusto() {
		return Custo;
	}

	public void setCusto(int custo) {
		Custo = custo;
	}

	public int getOrdIni() {
		return OrdIni;
	}

	public void setOrdIni(int ordIni) {
		OrdIni = ordIni;
	}

	public int getOrdFim() {
		return OrdFim;
	}

	public void setOrdFim(int ordFim) {
		OrdFim = ordFim;
	}

}
