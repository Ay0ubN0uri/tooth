import './footer.scss';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Container, Row } from 'reactstrap';

const Footer = () => (
  <footer
    className="text-light py-4"
    style={{
      backgroundColor: '#353d47',
    }}
  >
    <Container>
      <Row>
        <Col md={6}>
          <h5>Tooth</h5>
          <p>App for manging Dental University.</p>
        </Col>
        <Col md={3}></Col>
        <Col md={3}>
          <h5>Contact</h5>
          <address>
            <p>Email: a00n@nouri.com</p>
            <p>Phone: +123456789</p>
          </address>
        </Col>
      </Row>
    </Container>
  </footer>
);

export default Footer;
