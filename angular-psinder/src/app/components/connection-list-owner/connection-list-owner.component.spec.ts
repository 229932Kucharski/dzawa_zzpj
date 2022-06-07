import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConnectionListOwnerComponent } from './connection-list-owner.component';

describe('ConnectionListOwnerComponent', () => {
  let component: ConnectionListOwnerComponent;
  let fixture: ComponentFixture<ConnectionListOwnerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConnectionListOwnerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConnectionListOwnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
