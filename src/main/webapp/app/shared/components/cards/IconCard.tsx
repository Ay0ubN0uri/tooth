import React from 'react';
import { Card, CardBody, Col } from 'reactstrap';

const IconCard = ({ className = 'mb-4', icon, title, value }) => {
  return (
    <div className={`icon-row-item ${className}`}>
      <Card>
        <CardBody className="text-center">
          <i className={icon} />
          <p className="card-text font-weight-semibold mb-0">{title}</p>
          <p className="lead text-center">{value}</p>
        </CardBody>
      </Card>
    </div>
  );
};

const Colxx = props => <Col {...props} widths={['xxs', 'xs', 'sm', 'md', 'lg', 'xl', 'xxl']} />;
const Separator = ({ className }) => <div className={`separator ${className}`} />;
export { Colxx, Separator };

export default React.memo(IconCard);
