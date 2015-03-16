package com.mario.java.test.restful.api.hibernate.jpa.repository.util;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mario.java.restful.api.hibernate.jpa.repository.util.HibernateSessionManagerSingleton;
import com.mscharhag.oleaster.runner.OleasterRunner;

@RunWith(OleasterRunner.class)
public class HibernateSessionManagerSingletonTest {

	@Mock
	private SessionFactory sessionFactory;

	@Mock
	private Session session;

	@Mock
	private Transaction transaction;

	@InjectMocks
	private HibernateSessionManagerSingleton hibernateSessionManagerSingleton;

	{
		beforeEach(() -> {
			MockitoAnnotations.initMocks(this);

			Mockito.when(this.session.beginTransaction()).thenReturn(this.transaction);
			Mockito.when(this.sessionFactory.openSession()).thenReturn(this.session);
		});

		describe("#getSession", () -> {
			describe("when the session hasn't been assigned yet", () -> {
				it("returns null", () -> {
					expect(this.hibernateSessionManagerSingleton.getSession()).toBeNull();
				});
			});

			describe("when the session has already been assigned", () -> {
				beforeEach(() -> {
					this.hibernateSessionManagerSingleton.openSession();
				});

				afterEach(() -> {
					this.hibernateSessionManagerSingleton.closeSession();
				});

				it("returns the session", () -> {
					expect(this.hibernateSessionManagerSingleton.getSession()).equals(this.session);
				});
			});
		});

		describe("#openSession", () -> {

			beforeEach(() -> {
				this.hibernateSessionManagerSingleton.openSession();
			});

			it("opens the session", () -> {
				Mockito.verify(this.sessionFactory).openSession();
			});

			it("assigns the @session", () -> {
				expect(this.hibernateSessionManagerSingleton.getSession()).equals(this.session);
			});
		});

		describe("#closeSession", () -> {
			beforeEach(() -> {
				this.hibernateSessionManagerSingleton.openSession();
				this.hibernateSessionManagerSingleton.closeSession();
			});

			it("closes the session", () -> {
				Mockito.verify(this.session).close();
			});
		});

		describe("#openSessionWithTransaction", () -> {
			beforeEach(() -> {
				this.hibernateSessionManagerSingleton.openSessionWithTransaction();
			});

			it("opens a session", () -> {
				Mockito.verify(this.sessionFactory).openSession();
			});

			it("begins a transaction", () -> {
				Mockito.verify(this.session).beginTransaction();
			});
		});

		describe("#closeSessionWithTransaction", () -> {
			beforeEach(() -> {
				this.hibernateSessionManagerSingleton.openSessionWithTransaction();
				this.hibernateSessionManagerSingleton.closeSessionWithTransaction();
			});

			it("commits the transaction", () -> {
				Mockito.verify(this.transaction).commit();
			});

			it("closes the session", () -> {
				Mockito.verify(this.session).close();
			});
		});
	}
}