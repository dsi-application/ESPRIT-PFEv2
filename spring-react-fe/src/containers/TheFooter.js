import React from 'react'
import { CFooter } from '@coreui/react'

const TheFooter = () => {
  return (
    <CFooter fixed={false}>
      <div>
        <a href="https://esprit.tn/" target="_blank" rel="noopener noreferrer" style={{color:'darkred'}}>ESPRIT</a>
        <span className="ml-1">&copy; 2022-2023</span>
      </div>
    </CFooter>
  )
}

export default React.memo(TheFooter)
