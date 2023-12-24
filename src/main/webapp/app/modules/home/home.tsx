import './home.scss';

import React, { useState } from 'react';
import { Translate } from 'react-jhipster';
import { Row, Col } from 'reactstrap';

import { useAppSelector } from 'app/config/store';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';
import DashboardHome from './DashboardHome';
import LoadingSpinner from 'app/shared/components/LoadingSpinner';
import LandingHome from './LandingHome';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);
  const loading = useAppSelector(state => state.authentication.loading);
  const isAdminOrProfessor = useAppSelector(state =>
    hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN, AUTHORITIES.PROFESSOR]),
  );

  return (
    <Row>
      {loading ? (
        <LoadingSpinner />
      ) : (
        <>
          {isAdminOrProfessor ? (
            <>
              <Col md="3" className="pad">
                <span className="hipster rounded" />
              </Col>
              <Col md="9">
                <h1 className="display-4 d-flex">
                  <Translate contentKey="home.title">Welcome, </Translate>
                  <span>{`${account.firstName} ${account.lastName}`}</span>
                </h1>
                <DashboardHome />
              </Col>
            </>
          ) : (
            <LandingHome />
          )}
        </>
      )}
    </Row>
  );
};

export default Home;

{
  /* <p className="lead">
            <Translate contentKey="home.subtitle">This is your dashboard</Translate>
          </p> */
}
