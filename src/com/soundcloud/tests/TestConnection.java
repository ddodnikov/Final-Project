package com.soundcloud.tests;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;

import com.soundcloud.model.DBConnection;

public class TestConnection {

	@Test
	public void test() {
		Connection con = DBConnection.getDBInstance().getConnection();
		assertNotNull(con);
	}

}
