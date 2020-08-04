package com.kym.rs.idcard;

import com.google.gson.annotations.Expose;

public class IdCard {

	@Expose
	private Name Name;
	@Expose
	private Sex Sex;
	@Expose
	private Folk Folk;
	@Expose
	private Birt Birt;
	@Expose
	private Addr Addr;
	@Expose
	private Num Num;
	@Expose
	private Issue Issue;
	@Expose
	private Valid Valid;

	/**
	 * 
	 * @return The Name
	 */
	public Name getName() {
		return Name;
	}

	/**
	 * 
	 * @param Name
	 *            The Name
	 */
	public void setName(Name Name) {
		this.Name = Name;
	}

	/**
	 * 
	 * @return The Sex
	 */
	public Sex getSex() {
		return Sex;
	}

	/**
	 * 
	 * @param Sex
	 *            The Sex
	 */
	public void setSex(Sex Sex) {
		this.Sex = Sex;
	}

	/**
	 * 
	 * @return The Folk
	 */
	public Folk getFolk() {
		return Folk;
	}

	/**
	 * 
	 * @param Folk
	 *            The Folk
	 */
	public void setFolk(Folk Folk) {
		this.Folk = Folk;
	}

	/**
	 * 
	 * @return The Birt
	 */
	public Birt getBirt() {
		return Birt;
	}

	/**
	 * 
	 * @param Birt
	 *            The Birt
	 */
	public void setBirt(Birt Birt) {
		this.Birt = Birt;
	}

	/**
	 * 
	 * @return The Addr
	 */
	public Addr getAddr() {
		return Addr;
	}

	/**
	 * 
	 * @param Addr
	 *            The Addr
	 */
	public void setAddr(Addr Addr) {
		this.Addr = Addr;
	}

	/**
	 * 
	 * @return The Num
	 */
	public Num getNum() {
		return Num;
	}

	/**
	 * 
	 * @param Num
	 *            The Num
	 */
	public void setNum(Num Num) {
		this.Num = Num;
	}

	/**
	 * 
	 * @return The Issue
	 */
	public Issue getIssue() {
		return Issue;
	}

	/**
	 * 
	 * @param Issue
	 *            The Issue
	 */
	public void setIssue(Issue Issue) {
		this.Issue = Issue;
	}

	/**
	 * 
	 * @return The Valid
	 */
	public Valid getValid() {
		return Valid;
	}

	/**
	 * 
	 * @param Valid
	 *            The Valid
	 */
	public void setValid(Valid Valid) {
		this.Valid = Valid;
	}

}
