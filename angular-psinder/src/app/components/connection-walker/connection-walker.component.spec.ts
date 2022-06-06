import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConnectionWalkerComponent } from './connection-walker.component';

describe('ConnectionWalkerComponent', () => {
  let component: ConnectionWalkerComponent;
  let fixture: ComponentFixture<ConnectionWalkerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConnectionWalkerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConnectionWalkerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
